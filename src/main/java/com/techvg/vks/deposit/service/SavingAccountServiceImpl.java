package com.techvg.vks.deposit.service;

import com.techvg.vks.accounts.domain.AccountMapping;
import com.techvg.vks.accounts.service.AccountMappingService;
import com.techvg.vks.config.DepositConstants;
import com.techvg.vks.config.LoanConstants;
import com.techvg.vks.config.MappingName;
import com.techvg.vks.deposit.domain.DepositLedger;
import com.techvg.vks.deposit.domain.SavingAccount;
import com.techvg.vks.deposit.domain.SavingInterest;
import com.techvg.vks.deposit.mapper.DepositLedgerMapper;
import com.techvg.vks.deposit.mapper.SavingAccountMapper;
import com.techvg.vks.deposit.model.DepositLedgerDto;
import com.techvg.vks.deposit.model.SavingAccountDto;
import com.techvg.vks.deposit.model.SavingAccountPageList;
import com.techvg.vks.deposit.repository.DepositLedgerRepository;
import com.techvg.vks.deposit.repository.SavingAccountRepository;
import com.techvg.vks.deposit.repository.SavingInterestRepository;
import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.idgenerator.SavingsAccSeq;
import com.techvg.vks.idgenerator.repository.SavingsAccSeqRepository;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.repository.MemberRepository;
import com.techvg.vks.society.domain.DepositAccount;
import com.techvg.vks.society.repository.DepositAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SavingAccountServiceImpl implements SavingAccountService{
	
	private final  MemberRepository memberRepository ;
	private final  DepositAccountRepository depositAccountRepository;
	private final  SavingAccountMapper savingAccountMapper;
	private final DepositLedgerMapper depositLedgerMapper;

	private final SavingAccountRepository savingAccountRepository;
	
	private final DepositLedgerRepository depositLedgerRepository;
	
	private final SavingInterestRepository savingInterestRepository;
	private final SavingsAccSeqRepository seqRepository;

	private final AccountMappingService accountMappingService;

	@Override
	public SavingAccountDto addSavingAccountDetails(Long memberId, SavingAccountDto savingAccountDto) {

		Member member = memberRepository.findById(memberId).orElseThrow(
				() -> new NotFoundException("No Member found for Id : " +memberId));

		DepositAccount depositAccount = depositAccountRepository.findById(savingAccountDto.getDepositAccountId())
				.orElseThrow(NotFoundException::new);

		SavingAccount savingAccount = savingAccountMapper.savingAccountDtoToSavingAccount(savingAccountDto);
		Optional<SavingAccount> saving=savingAccountRepository.findByMemberIdAndStatus(savingAccountDto.getMemberId(),DepositConstants.DEPOSIT_ACC_OPEN);
		if(saving.isPresent()) {
			throw new AlreadyExitsException(
					"Saving Account already exists for this Member - " + member.getFirstName()+" "+member.getMiddleName()+" "+member.getLastName());

		}
		savingAccount.setMember(member);
		savingAccount.setDepositAccount(depositAccount);
		savingAccount.setStatus(DepositConstants.SAVING_ACCOUNT_OPEN);

		//Generate Saving A/C No
		SavingsAccSeq no = new SavingsAccSeq();
		seqRepository.save(no);
		savingAccount.setAccountNo(no.getSavingsAccNo());

		//Set parent acc head
		String mappingName = MappingName.SAVINGS_DEPOSIT;
		AccountMapping accountMapping = accountMappingService.getAccountMappingByName(mappingName);
		savingAccount.setParentAccHead(accountMapping.getLedgerAccHeadCode());

		savingAccount = savingAccountRepository.save(savingAccount);
		return savingAccountMapper.savingAccountToSavingAccountDto(savingAccount);
	}

	@Override
	public SavingAccountDto updateSavingAccountDetails(Long savingAccountId, SavingAccountDto savingAccountDto) {

		SavingAccount savingAccount = savingAccountRepository.findById(savingAccountId)
				.orElseThrow(() -> new NotFoundException("No Savings Account  found for Id : " +savingAccountId));
		savingAccountDto.setId(savingAccount.getId());
		savingAccountDto.setAccountNo(savingAccount.getAccountNo());

		SavingAccount savingAccounts = savingAccountMapper.savingAccountDtoToSavingAccount(savingAccountDto);

		savingAccounts.setMember(savingAccount.getMember());
		savingAccounts.setDepositAccount(savingAccount.getDepositAccount());
		savingAccounts = savingAccountRepository.save(savingAccounts);
		return savingAccountMapper.savingAccountToSavingAccountDto(savingAccounts);
	}

	@Override
	public SavingAccountDto getSavingAccountDetailsById(Long id) {

		SavingAccount savingAccount = savingAccountRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No saving account found for Id : " + id));
		return savingAccountMapper.savingAccountToSavingAccountDto(savingAccount);
	}

	@Override
	public SavingAccountDto deleteSavingAccountDetailsById(Long id) {

		SavingAccount savingAccount = savingAccountRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No saving account found for Id : " + id));
		if (savingAccount != null) {
			savingAccount.setIsDeleted(true);
			savingAccountRepository.save(savingAccount);
		}
		return savingAccountMapper.savingAccountToSavingAccountDto(savingAccount);
	}

	@Override
	public SavingAccountPageList listSavingAccount(Pageable pageable) {

		Page<SavingAccount> savingAccountPage;

		savingAccountPage = savingAccountRepository.findByisDeleted(pageable, false);
		return new SavingAccountPageList(
				savingAccountPage.getContent().stream().map(savingAccountMapper::savingAccountToSavingAccountDto)
						.collect(Collectors.toList()),
				PageRequest.of(savingAccountPage.getPageable().getPageNumber(),
						savingAccountPage.getPageable().getPageSize()),
				savingAccountPage.getTotalElements());

	}

	@Override
	public SavingAccountDto closeSavingAccount(Long memberId, Long savingAccountId) {
		SavingAccount savingAccount = savingAccountRepository.findById(savingAccountId)
				.orElseThrow(() -> new NotFoundException("No saving account found for Id : " + savingAccountId));

		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new NotFoundException("No Member found for Id : " + memberId));

		member.getLoanDetails().forEach(action -> {
			if (!(action.getLoanStatus()).equals(LoanConstants.LOAN_CLOSE)) {
				throw new AlreadyExitsException(
						"Existing Loan associated, Account Can not be closed : " + action.getLoanType()+" term loan ");
			}
		});
		// Interest calculation on saving account to be added
		savingAccount.setAccountClosingDate(new Date());
		savingAccount.setStatus(DepositConstants.SAVING_ACCOUNT_CLOSE);

		return savingAccountMapper.savingAccountToSavingAccountDto(savingAccountRepository.save(savingAccount));
	}
	
	

	@Override
	public Double calculateInterest(Long accountNo) {
		
		SavingAccount savingAccount = savingAccountRepository.findByAccountNo(accountNo)
				.orElseThrow(() -> new NotFoundException("No saving account found for account no : " + accountNo));
		
		double roi=savingAccount.getDepositAccount().getInterest();
	    log.info("roi :"+roi);  

	    Calendar cal = Calendar.getInstance();
	    cal.setTime(new java.util.Date());
	    cal.set(Calendar.DAY_OF_MONTH, 10);
	    cal.set(Calendar.HOUR, 00);
	   cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.MILLISECOND, 00);
		 
	    Date startDate=cal.getTime();
	    @SuppressWarnings("deprecation")
		Timestamp start=new Timestamp(startDate.getYear(),cal.get(Calendar.MONTH),
	    		cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE),
	    		cal.get(Calendar.SECOND),cal.get(Calendar.MILLISECOND));	

	    
	    log.info("start date:"+startDate);  
	    int endDay= cal.getActualMaximum(Calendar.DATE);
	    cal.set(Calendar.DAY_OF_MONTH, endDay);
	    cal.set(Calendar.HOUR, 23);
		 cal.set(Calendar.MINUTE, 59);
		 cal.set(Calendar.MILLISECOND, 59);
		 
	    Date endDate=cal.getTime();
	    log.info("end Date: "+endDate);
	    @SuppressWarnings("deprecation")
		Timestamp end=new Timestamp(endDate.getYear(),cal.get(Calendar.MONTH),
	    		cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE),
	    		cal.get(Calendar.SECOND),cal.get(Calendar.MILLISECOND));	

	    
	    //find minimum balance form deposit ledger using jpql
	    double minBalance=depositLedgerRepository.findByBalanceAmount(accountNo, start, end);
	    log.info("minBalance"+minBalance);
	    
		//interest calculate of current month
		 long currentTime = endDate.getTime();
		 long startTime = startDate.getTime();
		 long diffTillDate = currentTime - startTime;
		 long duration = (int) (diffTillDate / (24 * 60 * 60 * 1000));
		 log.info("duration"+duration);
		 double interest=minBalance*roi/100/duration;
		 log.info("interest"+interest);
	
		 SavingInterest savingInterest=new SavingInterest();
		 savingInterest.setMonthlyInterest(interest);
		 savingInterest.setMonthlyBalance(minBalance);
		 savingInterest.setAccountNo(savingAccount.getAccountNo());
		 savingInterest.setYear(cal.get(Calendar.YEAR));
		 savingInterest.setMonth(cal.get(Calendar.MONTH));

		 savingInterestRepository.save(savingInterest);
 
		return interest;
	}

	@Override
	public Double calculateInterest(Long accountNo, Integer year) {
			
		return savingInterestRepository.findByMonthlyInterest(accountNo,year);
	}

	public DepositLedgerDto postSavingInterest(Long accountNo, Integer year)
	{
		double totalInterest = calculateInterest(accountNo, year);
		
		DepositLedger depositLedger1 = depositLedgerRepository.findByLastRecord(accountNo);
		DepositLedgerDto depositLedgerDto = DepositLedgerDto.builder()
												.accountNo(accountNo)
												.creditAmount(totalInterest)
												.depositDate(new Date())
												.balanceAmount(depositLedger1.getBalanceAmount()+totalInterest)
												.narration(DepositConstants.INTEREST_DEPOSIT_YEAR + year)
												.particulars(DepositConstants.SAVING_ACCOUNT + accountNo + DepositConstants.INTEREST_DEPOSIT_CREDITED)
												.build();
		
		return depositLedgerMapper.depositLedgerToDepositLedgerDto(depositLedgerRepository.save(depositLedgerMapper.depositLedgerDtoToDepositLedger(depositLedgerDto)));
	}

	@Override
	public SavingAccountDto getSavingByMemberId(Long memberId) {
		SavingAccount saving=savingAccountRepository.findByMemberId(memberId);
		SavingAccountDto savingDto=savingAccountMapper.savingAccountToSavingAccountDto(saving);
		return savingDto;
	}

	@Override
	public double getSavingAccountCount() {
		double savingAccountCount=savingAccountRepository.findSavingAccountCount();
		return savingAccountCount;
	}
	

}
