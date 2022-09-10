package com.techvg.vks.deposit.service;

import com.techvg.vks.accounts.domain.DayBook;
import com.techvg.vks.accounts.model.CashBookDto;
import com.techvg.vks.accounts.repository.DayBookRepository;
import com.techvg.vks.accounts.service.CashBookService;
import com.techvg.vks.config.AccountConstants;
import com.techvg.vks.config.DepositConstants;
import com.techvg.vks.deposit.domain.Deposit;
import com.techvg.vks.deposit.domain.DepositLedger;
import com.techvg.vks.deposit.domain.SavingAccount;
import com.techvg.vks.deposit.mapper.DepositLedgerMapper;
import com.techvg.vks.deposit.model.DepositLedgerDto;
import com.techvg.vks.deposit.repository.DepositLedgerRepository;
import com.techvg.vks.deposit.repository.DepositRepository;
import com.techvg.vks.deposit.repository.SavingAccountRepository;
import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepositLedgerServiceImpl implements DepositLedgerService {

	private final DepositLedgerMapper depositLedgerMapper;
	private final DepositLedgerRepository depositLedgerRepository;
	private final SavingAccountRepository  savingAccountRepository;
	private final DepositRepository  depositRepository;
	private final CashBookService cashBookService;
	private final MemberRepository memberRepository;
	private final DayBookRepository dayBookRepo;
	Member member;

	@Override
	public DepositLedgerDto creditDepositAccount(DepositLedgerDto depositLedgerDto, Authentication authentication) {
		return depositLedgerMapper.depositLedgerToDepositLedgerDto(creditDeposit(depositLedgerDto));
	}

	@Override
	public DepositLedger creditDeposit(DepositLedgerDto depositLedgerDto) {
		log.debug("REST request to save Prerequisite : {}", depositLedgerDto);
		boolean isRd = false;
		DepositLedger depositLedger = depositLedgerMapper.depositLedgerDtoToDepositLedger(depositLedgerDto);
			Deposit deposit=depositRepository.findById(depositLedgerDto.getDepositId()).orElseThrow(
					() -> new NotFoundException("No deposit Account found for Id : " +depositLedgerDto.getDepositId()));
			depositLedger.setAccountNo(deposit.getAccountNo());
			depositLedger.setDeposit(deposit);
			depositLedger.setDebitAmount(0.0);
			DepositLedger deposit1 = depositLedgerRepository.findByLastRecordForRD(deposit.getAccountNo(), deposit.getId());
			if(deposit.getRecurringAmount()!=0)  {
				isRd = true;
				if (deposit1 == null) {
					if(deposit.getRecurringAmount().equals(depositLedgerDto.getCreditAmount()))
					{
						depositLedger.setBalanceAmount(depositLedgerDto.getCreditAmount());
					}
					else {
						throw new AlreadyExitsException(" For your RD Account, Recurring Amount is: " + deposit.getRecurringAmount());
					}
					
				} else

				{
					if(deposit.getRecurringAmount().equals(depositLedgerDto.getCreditAmount()))
					{
					depositLedger.setBalanceAmount((deposit1.getBalanceAmount()) + (depositLedgerDto.getCreditAmount()));
				}
					else {
						throw new AlreadyExitsException(" For your RD Account, Recurring Amount is: " + deposit.getRecurringAmount());
					}
				}
			}
			else {
				if (deposit1 == null) {
						depositLedger.setBalanceAmount(depositLedgerDto.getCreditAmount());
				} else

				{
					depositLedger.setBalanceAmount((deposit1.getBalanceAmount()) + (depositLedgerDto.getCreditAmount()));
				}
			}

		depositLedger.setDate(new Date());
		depositLedgerRepository.save(depositLedger);
		if(isRd)
			updateCashBookEntry(depositLedger, AccountConstants.DEBIT, DepositConstants.DEPOSIT_ACC_TYPE_RD);
		else
			updateCashBookEntry(depositLedger, AccountConstants.DEBIT, DepositConstants.DEPOSIT_ACC_TYPE_FD);

		return depositLedger;
	}

	public DepositLedger debitDeposit(DepositLedgerDto depositLedgerDto){
		DepositLedger depositLedger = depositLedgerMapper.depositLedgerDtoToDepositLedger(depositLedgerDto);
		Deposit deposit=depositRepository.findById(depositLedgerDto.getDepositId()).orElseThrow(
				() -> new NotFoundException("No deposit Account found for Id : " +depositLedgerDto.getDepositId()));
		depositLedger.setAccountNo(deposit.getAccountNo());
		depositLedger.setDeposit(deposit);
		depositLedger.setCreditAmount(0.0);
		DepositLedger deposit1 = depositLedgerRepository.findByLastRecordForRD(deposit.getAccountNo(), deposit.getId());
		if (deposit1 == null) {
			throw new NotFoundException(" No balance in account: " + deposit.getAccountNo());
		}else{
			depositLedger.setBalanceAmount((deposit1.getBalanceAmount()) - (depositLedgerDto.getDebitAmount()));
		}
		depositLedger.setDate(new Date());
		depositLedgerRepository.save(depositLedger);

		if(deposit.getRecurringAmount()!=0)
			updateCashBookEntry(depositLedger, AccountConstants.CREDIT, DepositConstants.DEPOSIT_ACC_TYPE_RD);
		else
			updateCashBookEntry(depositLedger, AccountConstants.CREDIT, DepositConstants.DEPOSIT_ACC_TYPE_FD);

		return depositLedger;
	}

	@Override
	public DepositLedgerDto creditSavingAccount(DepositLedgerDto depositLedgerDto, Authentication authentication) {
		log.debug("REST request to save Prerequisite : {}", depositLedgerDto);
		String transType=null;
		DepositLedger depositLedger = depositLedgerMapper.depositLedgerDtoToDepositLedger(depositLedgerDto);

			SavingAccount savingAccount=savingAccountRepository.findById(depositLedgerDto.getSavingAccountId()).orElseThrow(
					() -> new NotFoundException("No saving Account found for Id : " +depositLedgerDto.getSavingAccountId()));
			depositLedger.setAccountNo(savingAccount.getAccountNo());
			depositLedger.setSavingAccount(savingAccount);
			depositLedger.setDayBookCreated(false);
			DepositLedger deposit1 = depositLedgerRepository.findByLastRecordForSavings(savingAccount.getAccountNo(), savingAccount.getId());
			
			if (deposit1 == null && depositLedgerDto.getDebitAmount()!=null) {
				throw new AlreadyExitsException("1st entry should not be debit ");

			}
			if (deposit1 == null) {
				depositLedger.setBalanceAmount(depositLedgerDto.getCreditAmount());
				transType = AccountConstants.DEBIT;
			} else

			{
				if(depositLedgerDto.getCreditAmount()!=null) {
					depositLedger.setBalanceAmount((deposit1.getBalanceAmount()) + (depositLedgerDto.getCreditAmount()));
					transType = AccountConstants.DEBIT;
				}
				else {
					if(depositLedgerDto.getDebitAmount()!=null) {
						depositLedger.setBalanceAmount((deposit1.getBalanceAmount()) - (depositLedgerDto.getDebitAmount()));
						
						if(depositLedgerDto.getDebitAmount()> deposit1.getBalanceAmount()) {
							throw new AlreadyExitsException("Insufficient balance amount, current available balance is " +deposit1.getBalanceAmount());

						}
						transType = AccountConstants.CREDIT;
					}
				}
			}

		depositLedger.setDate(new Date());
		depositLedgerRepository.save(depositLedger);
		updateCashBookEntry(depositLedger, transType, DepositConstants.DEPOSIT_ACC_TYPE_SAVINGS);

		return depositLedgerMapper.depositLedgerToDepositLedgerDto(depositLedger);
	}

	private boolean updateCashBookEntry(DepositLedger depositLedger, String transType, String accType){
		CashBookDto cashBookDto = CashBookDto.builder()
				.transDate(depositLedger.getDepositDate())
				.creditAmt(depositLedger.getDebitAmount())
				.debitAmt(depositLedger.getCreditAmount())
				.transType(transType)
				.isDeleted(false)
				.build();
		if(accType.equalsIgnoreCase(DepositConstants.DEPOSIT_ACC_TYPE_SAVINGS))
			cashBookDto.setParticulars(depositLedger.getSavingAccount().getParentAccHead()+" - "+depositLedger.getAccountNo());
		if(accType.equalsIgnoreCase(DepositConstants.DEPOSIT_ACC_TYPE_RD) || accType.equalsIgnoreCase(DepositConstants.DEPOSIT_ACC_TYPE_FD))
			cashBookDto.setParticulars(depositLedger.getDeposit().getParentAccHead()+" - "+depositLedger.getAccountNo());

		cashBookService.addCashbookEntry(cashBookDto);
		return true;
	}

	@Override
	public List<DepositLedgerDto> listDepositLedger(Long id) {
		Optional<Deposit> deposit=depositRepository.findById(id);
		if(deposit.isPresent()) {
			 member = memberRepository.findById(deposit.get().getMember().getId()).orElseThrow(() -> new NotFoundException("No member found for : " + deposit.get().getMember().getId()));
		}
		
		Optional<SavingAccount>  savingAccount=savingAccountRepository.findById(id);
		if(savingAccount.isPresent()) {
			 member = memberRepository.findById(savingAccount.get().getMember().getId()).orElseThrow(() -> new NotFoundException("No member found for : " + savingAccount.get().getMember().getId()));
		}
		List<DepositLedger> depositledger=depositLedgerRepository.findLastRecordsById(id);
		List<DepositLedgerDto> deposit2=depositLedgerMapper.toDtoList(depositledger);
		deposit2.forEach(action->{
			action.setFullName(member.getFirstName()+" "+member.getMiddleName()+" "+member.getLastName());
		});
		return deposit2;
	}


	@Override
	public double getRecurringAmount() {
	List<Long> rdList=depositLedgerRepository.findRecurringAmount();
		double sum=0;
		for(int j=0;j<rdList.size();j++) {
			Optional<Deposit> deposit=depositRepository.findById(rdList.get(j));

			DepositLedger depositLedger=depositLedgerRepository.findByLastRecordForRD(deposit.get().getAccountNo(), deposit.get().getId());
			sum=sum+depositLedger.getBalanceAmount();
		}
		return sum;
	}


	@Override
	public DepositLedgerDto getLastRecord(Long accountNo) {
		DepositLedger lastRecord=depositLedgerRepository.findByLastRecord(accountNo);
		DepositLedgerDto lastRecordDto=depositLedgerMapper.depositLedgerToDepositLedgerDto(lastRecord);
		return lastRecordDto;
	}

	@Override
	public List<DepositLedger> getLedgerEntries(Date transDate) {
		return depositLedgerRepository.findByDepositDate(transDate, transDate);
	}

	@Override
	public boolean populateDayBook(Date transDate) {

		double balance = 0.0;
		DayBook dayBook2 = dayBookRepo.findByLastRecord(transDate);
		if(dayBook2 !=null)
			balance = dayBook2.getBalance();

		DayBook dayBook1 = dayBookRepo.findByLastRecordOfTheDay(transDate);
		if(dayBook1 !=null)
			balance = dayBook1.getBalance();

		List<DepositLedger> ledgerList = getLedgerEntries(transDate);

		for (DepositLedger ledger:ledgerList) {
			DayBook dayBook = new DayBook();
			dayBook.setMode(AccountConstants.CASH);

			if(ledger.getSavingAccount() !=null){
				dayBook.setParticulars(ledger.getSavingAccount().getParentAccHead());
				dayBook.setAccHeadCode(ledger.getSavingAccount().getParentAccHead());
			}else if(ledger.getDeposit() !=null){
				dayBook.setParticulars(ledger.getDeposit().getParentAccHead());
				dayBook.setAccHeadCode(ledger.getDeposit().getParentAccHead());
			}
			if(ledger.getCreditAmount() !=null){
				dayBook.setTransType(AccountConstants.DEBIT);
			}else if(ledger.getDebitAmount() !=null){
				dayBook.setTransType(AccountConstants.CREDIT);
			}
			dayBook.setTransDate(ledger.getDepositDate());

			dayBook = addDayBookEntry(dayBook, ledger);

			if(dayBook.getTransType().equalsIgnoreCase(AccountConstants.DEBIT)) {
				balance = balance + ledger.getCreditAmount();
				dayBook.setBalance(balance );
			}
			else if(dayBook.getTransType().equalsIgnoreCase(AccountConstants.CREDIT)) {
				balance = balance - ledger.getDebitAmount();
				dayBook.setBalance(balance);
			}
			dayBookRepo.save(dayBook);
			ledger.setDayBookCreated(true);
			depositLedgerRepository.save(ledger);
		}
		return true;
	}

	private DayBook  addDayBookEntry(DayBook dayBook, DepositLedger ledger){

		Optional<DayBook> dayBookOptional = dayBookRepo.findByParticularsAndTransDateAndTransTypeOrderByIdAsc(
				dayBook.getParticulars(), dayBook.getTransDate(), dayBook.getTransType());

		if(dayBook.getTransType().equalsIgnoreCase(AccountConstants.DEBIT)) {
			dayBook = addDebitEntry(dayBookOptional, dayBook, ledger);
		}
		else if(dayBook.getTransType().equalsIgnoreCase(AccountConstants.CREDIT)) {
			dayBook = addCreditEntry(dayBookOptional, dayBook, ledger);
		}

		return dayBook;
	}

	private DayBook addDebitEntry(Optional<DayBook> dayBookOptional, DayBook dayBook, DepositLedger ledger) {
		Integer voucherCount=0;
		double debitTotalAmt=0.0;
		double debitCashAmt=0.0;
		double debitTransferAmt=0.0;
		double debitBalance = 0.0;

		if(dayBookOptional.isPresent()){
			dayBook = dayBookOptional.get();
			voucherCount = dayBook.getVoucherCount();
			debitTotalAmt = dayBook.getDebitTotalAmt();
			debitCashAmt = dayBook.getDebitCashAmt()==null?0.0:dayBook.getDebitCashAmt();
			debitTransferAmt = dayBook.getDebitTransferAmt()==null?0.0:dayBook.getDebitTransferAmt();
			debitBalance = dayBook.getDebitBalance();
		}

		dayBook.setVoucherCount(voucherCount + 1);
		dayBook.setDebitTotalAmt( debitTotalAmt + ledger.getCreditAmount());
		dayBook.setDebitBalance(debitBalance + ledger.getCreditAmount());

		if(dayBook.getMode().equalsIgnoreCase(AccountConstants.CASH)){
			dayBook.setDebitCashAmt(debitCashAmt + ledger.getCreditAmount());
			dayBook.setDebitTransferAmt(debitTransferAmt);
		}
		else{
			dayBook.setDebitTransferAmt(debitTransferAmt + ledger.getCreditAmount());
			dayBook.setDebitCashAmt(debitCashAmt);
		}

		return dayBook;
	}

	private DayBook addCreditEntry(Optional<DayBook> dayBookOptional, DayBook dayBook, DepositLedger ledger) {

		Integer voucherCount=0;
		double creditTotalAmt=0.0;
		double creditCashAmt=0.0;
		double creditTransferAmt=0.0;
		double creditBalance = 0.0;

		if(dayBookOptional.isPresent()){
			dayBook = dayBookOptional.get();
			voucherCount = dayBook.getVoucherCount();
			creditTotalAmt = dayBook.getCreditTotalAmt();
			creditCashAmt = dayBook.getCreditCashAmt()==null?0.0:dayBook.getCreditCashAmt();
			creditTransferAmt = dayBook.getCreditTransferAmt()==null?0.0:dayBook.getCreditTransferAmt();
			creditBalance = dayBook.getCreditBalance();
		}

		dayBook.setVoucherCount(voucherCount + 1);
		dayBook.setCreditTotalAmt( creditTotalAmt + ledger.getDebitAmount());
		dayBook.setCreditBalance(creditBalance + ledger.getDebitAmount());

		if(dayBook.getMode().equalsIgnoreCase(AccountConstants.CASH)){
			dayBook.setCreditCashAmt(creditCashAmt + ledger.getDebitAmount());
			dayBook.setCreditTransferAmt(creditTransferAmt);
		}
		else{
			dayBook.setCreditTransferAmt(creditTransferAmt + ledger.getDebitAmount());
			dayBook.setCreditCashAmt(creditCashAmt);
		}

		return dayBook;
	}
}
