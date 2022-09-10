package com.techvg.vks.batchjobs;

import com.techvg.vks.accounts.domain.AccountMapping;
import com.techvg.vks.accounts.service.AccountMappingService;
import com.techvg.vks.common.DateConverter;
import com.techvg.vks.config.DepositConstants;
import com.techvg.vks.config.MappingName;
import com.techvg.vks.deposit.domain.Deposit;
import com.techvg.vks.deposit.domain.DepositLedger;
import com.techvg.vks.deposit.model.DepositAccrualDto;
import com.techvg.vks.deposit.repository.DepositRepository;
import com.techvg.vks.deposit.service.DepositAccrualService;
import com.techvg.vks.idgenerator.AccruedAccSeq;
import com.techvg.vks.idgenerator.repository.AccruedAccSeqRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepositInterest {

    private final DepositRepository depositRepository;
    private final AccountMappingService accountMappingService;
    private final DepositAccrualService accrualService;
    private final AccruedAccSeqRepository accruedAccSeqRepository;


    @Scheduled(cron = "00 49 23 ? * *")
    @Transactional
    public void postMonthlyDepositInterest() {
        List<Deposit> depositList = depositRepository.findByDepositStatusAndIsDeleted(DepositConstants.DEPOSIT_ACC_OPEN, false);
        for(Deposit deposit:depositList){
            double interestAccrued=0.0;
            double roi = deposit.getDepositAccount().getInterest();
            int dayDiff = 1;

            LocalDate currentDate = LocalDate.now() ;
            LocalDate periodFrom = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);
            LocalDate periodTo = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getMonth().maxLength());

            LocalDate depositDate = deposit.getDepositDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate maturityDate = deposit.getMaturityDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if(depositDate.getMonth() == currentDate.getMonth() &&
                    depositDate.getYear() == currentDate.getYear()){
                if(depositDate.isBefore(currentDate)  ) {
                    dayDiff = DateConverter.dayTimeDiff(depositDate, currentDate);
                    periodFrom = depositDate;
                    periodTo = currentDate;
                }
            }else if(maturityDate.getMonth() == currentDate.getMonth() &&
                    maturityDate.getYear() == currentDate.getYear()){
                         dayDiff = maturityDate.getDayOfMonth();
                         periodTo = maturityDate;
            } else if( (depositDate.getMonth() == currentDate.getMonth() && depositDate.getYear() == currentDate.getYear()) &&
                    (maturityDate.getMonth() == currentDate.getMonth() && maturityDate.getYear() == currentDate.getYear()) ){
                dayDiff = DateConverter.dayTimeDiff(depositDate, maturityDate);
                periodFrom = depositDate;
                periodTo = maturityDate;
            }
            else {
                dayDiff = currentDate.lengthOfMonth();
            }

            double balance=0.0;
            Set<DepositLedger> ledgerSet = deposit.getDepositLedger();
            for(DepositLedger ledger:ledgerSet){
                balance = ledger.getBalanceAmount();
            }

            interestAccrued = (balance * (roi / 100 /365)) * dayDiff;

            DepositAccrualDto dto = DepositAccrualDto.builder()
                                        .accrualDate(currentDate)
                                        .depositId(deposit.getId())
                                        .interestAccrued(interestAccrued)
                                        .debit(interestAccrued)
                                        .credit(0.0)
                                        .isPosted(false)
                                        .periodFrom(periodFrom)
                                        .periodTo(periodTo)
                                        .build();
            AccountMapping accountMapping = accountMappingService.getAccountMappingByName(MappingName.DEPOSIT_ACCRUED_INTEREST_RD);
            if(deposit.getRecurringAmount() == 0.0)
                 accountMapping = accountMappingService.getAccountMappingByName(MappingName.DEPOSIT_ACCRUED_INTEREST_FD);
            dto.setParentAccHead(accountMapping.getLedgerAccHeadCode());

            if(deposit.getAccrualAccountNo() == null) {
                //Generate Accrual A/C No
                AccruedAccSeq no = new AccruedAccSeq();
                accruedAccSeqRepository.save(no);
                deposit.setAccrualAccountNo(no.getAccruedAccNo());
                depositRepository.save(deposit);
            }
            dto.setAccountNo(deposit.getAccrualAccountNo());
            accrualService.accrualInterestPosting(dto);
        }

    }

}
