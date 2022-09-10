package com.techvg.vks.deposit.service;

import com.techvg.vks.accounts.domain.AccountMapping;
import com.techvg.vks.accounts.service.AccountMappingService;
import com.techvg.vks.accounts.service.vouchers.DepositPaymentVoucherService;
import com.techvg.vks.config.DepositConstants;
import com.techvg.vks.config.MappingName;
import com.techvg.vks.deposit.domain.Deposit;
import com.techvg.vks.deposit.domain.DepositAccrual;
import com.techvg.vks.deposit.domain.DepositLedger;
import com.techvg.vks.deposit.mapper.DepositMapper;
import com.techvg.vks.deposit.model.DepositAccrualDto;
import com.techvg.vks.deposit.model.DepositDto;
import com.techvg.vks.deposit.model.DepositLedgerDto;
import com.techvg.vks.deposit.repository.DepositRepository;
import com.techvg.vks.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class PreMatureClosureServiceImpl implements PreMatureClosureService {
	
	private final DepositRepository depositRepository;
	private final DepositMapper   depositMapper;
	private final DepositService depositService;
	private final AccountMappingService accountMappingService;
	private final DepositPaymentVoucherService depositPaymentVoucherService;
	private final DepositAccrualService accrualService;
	private final DepositLedgerService depositLedgerService;
	
	@Override
	public DepositDto preMatureDepositClose(Long depositId) {
		
		Deposit deposit = depositRepository.findById(depositId).orElseThrow(
				() -> new NotFoundException("No Deposit found for Id : " +depositId));
		
			if(new Date().compareTo(deposit.getMaturityDate()) < 0 )
			{
				deposit.setDepositStatus(DepositConstants.PREMATURE_CLOSE);
				deposit.setDepositClosingDate(new Date());
				//deposit.setMaturityAmount(deposit.getDepositAmount());
				double interestAmt=depositService.calculateInterest(depositId, new Date());
				System.out.println("amt----------------------"+interestAmt);
				if(deposit.getRecurringAmount()==0){
					deposit.setMaturityAmount(interestAmt + deposit.getDepositAmount());
					deposit.setInterestEarned(interestAmt);
				}else{
					Double recurringAmt=0.0;
					Set<DepositLedger> ledgerSet = deposit.getDepositLedger();
					for(DepositLedger ledger:ledgerSet){
						recurringAmt = ledger.getBalanceAmount();
					}
					deposit.setInterestEarned(interestAmt);
					deposit.setMaturityAmount(interestAmt + recurringAmt);
				}
				deposit=depositRepository.save(deposit);
				
			}
			else {
					
				    deposit.setDepositStatus(DepositConstants.FD_RD_CLOSE);
					deposit.setDepositClosingDate(new Date());
					deposit=depositRepository.save(deposit);
			}

		String particulars = "Being payment of deposit A/C no \n" + deposit.getAccountNo() + " of " +
				deposit.getMember().getFirstName() + " " + deposit.getMember().getLastName() + "\n";
		//Update deposit ledger, Cash book, vouchers
		debitDepositLedger(deposit, particulars);
		depositPaymentVoucherService.depositPaymentVoucher(deposit);
		//Update deposit accrued ledger
		creditDepositAccruedLedger(deposit);
			return depositMapper.depositToDepositDto(deposit);
			
	}

	private boolean creditDepositAccruedLedger(Deposit deposit){
		List<DepositAccrualDto> depositAccrualList = accrualService.getAccrualsForDeposit(deposit.getId());
		if(depositAccrualList !=null && depositAccrualList.size() >0) {
			double interestAccrued = deposit.getDepositAccruals().stream().mapToDouble(DepositAccrual::getInterestAccrued).sum();
			DepositAccrualDto dto = DepositAccrualDto.builder()
					.accrualDate(LocalDate.now())
					.depositId(deposit.getId())
					.interestAccrued(0.0)
					.debit(0.0)
					.credit(interestAccrued)
					.isPosted(true)
					.periodFrom(deposit.getCreated().toLocalDate())
					.periodTo(LocalDate.now())
					.build();
			AccountMapping accountMapping = accountMappingService.getAccountMappingByName(MappingName.DEPOSIT_ACCRUED_INTEREST_RD);
			if (deposit.getRecurringAmount() == 0.0)
				accountMapping = accountMappingService.getAccountMappingByName(MappingName.DEPOSIT_ACCRUED_INTEREST_FD);
			dto.setParentAccHead(accountMapping.getLedgerAccHeadCode());
			accrualService.accrualInterestPosting(dto);
		}
		return true;
	}

	private DepositLedger debitDepositLedger(Deposit deposit, String particulars){

		DepositLedgerDto depositLedgerDto = DepositLedgerDto.builder()
				.accountNo(deposit.getAccountNo())
				.debitAmount(deposit.getMaturityAmount())
				.creditAmount(0.0)
				.depositDate(new Date())
				.depositId(deposit.getId())
				.particulars(particulars)
				.isDeleted(false)
				.dayBookCreated(true)
				.build();
		return depositLedgerService.debitDeposit(depositLedgerDto);
	}

}
