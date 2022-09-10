package com.techvg.vks.deposit.service;

import com.techvg.vks.accounts.domain.AccountMapping;
import com.techvg.vks.accounts.service.AccountMappingService;
import com.techvg.vks.accounts.service.vouchers.DepositPaymentVoucherService;
import com.techvg.vks.config.DepositConstants;
import com.techvg.vks.config.MappingName;
import com.techvg.vks.deposit.domain.*;
import com.techvg.vks.deposit.mapper.DepositMapper;
import com.techvg.vks.deposit.model.DepositAccrualDto;
import com.techvg.vks.deposit.model.DepositDto;
import com.techvg.vks.deposit.model.DepositLedgerDto;
import com.techvg.vks.deposit.model.DepositPageList;
import com.techvg.vks.deposit.repository.DepositLedgerRepository;
import com.techvg.vks.deposit.repository.DepositRepository;
import com.techvg.vks.deposit.repository.SavingAccountRepository;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.idgenerator.FDAccSeq;
import com.techvg.vks.idgenerator.RDAccSeq;
import com.techvg.vks.idgenerator.repository.FDAccSeqRepository;
import com.techvg.vks.idgenerator.repository.RDAccSeqRepository;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.repository.MemberRepository;
import com.techvg.vks.society.domain.DepositAccount;
import com.techvg.vks.society.repository.DepositAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepositServiceImpl implements DepositService {

	private final MemberRepository memberRepository;
	private final DepositRepository depositRepository;
	private final DepositMapper depositMapper;
	private final DepositLedgerService depositLedgerService;
	private final RDAccSeqRepository rdSeqRepository;
	private final FDAccSeqRepository fdSeqRepository;

	private final DepositAccountRepository depositAccountRepository;
	private final SavingAccountRepository savingAccountRepository;
	private final DepositLedgerRepository depositLedgerRepository;
	private final AccountMappingService accountMappingService;
	private final DepositPaymentVoucherService depositPaymentVoucherService;
	private final DepositAccrualService accrualService;

	@Override
	@Transactional
	public DepositDto addNewDeposit(DepositDto depositDto, Long memberId,Long depositAccountId, Authentication authentication) {

		String mappingName = null;

		Member member = memberRepository.findById(memberId).orElseThrow(NotFoundException::new);
		DepositAccount depositAccount = depositAccountRepository.findById(depositAccountId).orElseThrow(
				() -> new NotFoundException("No Deposit Account found for Id : " +depositAccountId));
		Deposit deposit = depositMapper.depositDtoToDeposit(depositDto); 
		Double recurringAmt =deposit.getRecurringAmount();
		
		Integer frequency=deposit.getDepositFrequency();		
		
	    boolean isRecuringDeposit=false;
		if(depositAccount.depositType.getAccountType().equals(DepositConstants.DEPOSIT_ACC_TYPE_RD))
		{
		Date d1=	getMaturityDate(deposit.getDepositDate(),depositAccount.getRdDurationMonths(),false);
		deposit.setMaturityDate(d1);
			isRecuringDeposit=true;
			double depositAmt=recurringAmt*(depositAccount.getRdDurationMonths()/frequency);
			deposit.setDepositAmount(depositAmt);
					
		}

		deposit.setMember(member);
		deposit.setDepositAccount(depositAccount);
		deposit.setDepositStatus(DepositConstants.DEPOSIT_ACC_OPEN);
	
		deposit = depositRepository.save(deposit);

		//need to call jyoti's method to get interest and calculate maturity amount
		
		if(isRecuringDeposit)
		{
			//set maturity amount for recurring deposit
		double maturityAmt = calculateRDMaturityAmt(depositAccount, deposit );
			deposit.setMaturityAmount(maturityAmt);
			
			//Generate RD A/C No
			RDAccSeq no = new RDAccSeq();
			rdSeqRepository.save(no);
			deposit.setAccountNo(no.getRdAccNo());
			deposit.setReceiptNo(no.getRdAccNo());
			mappingName = MappingName.RECURRING_DEPOSIT;
		}	
		else
		{		
			Date d1=	getMaturityDate(deposit.getDepositDate(),depositAccount.getFdDurationDays(),true);
			deposit.setMaturityDate(d1);
			DepositInterestCalculation depositInterestCalculation= calculateInterest(deposit.getId());
			deposit.setMaturityAmount(depositInterestCalculation.getMaturityAmount());
			deposit.setInterestEarned(depositInterestCalculation.getInterest());
			//Generate FD A/C No
			FDAccSeq no = new FDAccSeq();
			fdSeqRepository.save(no);
			deposit.setAccountNo(no.getFdAccNo());
			deposit.setReceiptNo(no.getFdAccNo());
			mappingName = MappingName.FIXED_DEPOSIT;
		}

		//Set parent acc head
		AccountMapping accountMapping = accountMappingService.getAccountMappingByName(mappingName);
		deposit.setParentAccHead(accountMapping.getLedgerAccHeadCode());
		deposit.setAccHead(accountMapping.getLedgerAccHeadCode() + " A/C No " + deposit.getAccountNo());

		deposit = depositRepository.save(deposit);

		String particulars = "Recurring deposit opened for Rs "+deposit.getDepositAmount()+ " A/C No "+deposit.getAccountNo();
		if(!isRecuringDeposit){
			particulars = "Fixed deposit added for Rs "+deposit.getDepositAmount()+ " A/C No "+deposit.getAccountNo();
		}
		creditDepositLedger(deposit, particulars);
		return depositMapper.depositToDepositDto(deposit);
	}

	private DepositLedger creditDepositLedger(Deposit deposit, String particulars){
		DepositLedgerDto depositLedgerDto = DepositLedgerDto.builder()
											.accountNo(deposit.getAccountNo())
											.balanceAmount(deposit.getDepositAmount())
											.debitAmount(0.0)
											.depositDate(deposit.getDepositDate())
											.depositId(deposit.getId())
											.particulars(particulars)
											.dayBookCreated(false)
											.isDeleted(false)
											.build();
		if(deposit.getRecurringAmount() != 0){
			depositLedgerDto.setCreditAmount(deposit.getRecurringAmount());
		}
		else{
			depositLedgerDto.setCreditAmount(deposit.getDepositAmount());

		}
		return depositLedgerService.creditDeposit(depositLedgerDto);
	}

	private Date getMaturityDate(Date date,int duration,boolean isFD) {
		Date maturityDate=date;
		Calendar cal = Calendar.getInstance();
	    cal.setTime(maturityDate);
		if(isFD) {
			
		    cal.add(Calendar.DATE,duration);
		  
		}else {
			cal.add(Calendar.MONTH, duration);
		}
		  return cal.getTime(); 
	}

	public DepositPageList listAllDeposits(Pageable pageable) {
		log.debug("Request to get deposits : {}");
		Page<Deposit> depositPage;
		depositPage = depositRepository.findByisDeleted(pageable, false);
		return new DepositPageList(depositPage
										.getContent()
										.stream()
										.map(depositMapper::depositToDepositDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(depositPage.getPageable().getPageNumber(),
											depositPage.getPageable().getPageSize()),
											depositPage.getTotalElements());

	}

	@Override
	public DepositDto deleteDepositById(Long depositId) {
		Deposit deposit = depositRepository.findById(depositId).orElseThrow(
				() -> new NotFoundException("No Deposit found for Id : " +depositId));
		if (deposit != null) {
			deposit.setIsDeleted(true);
			depositRepository.save(deposit);
			}
		return depositMapper.depositToDepositDto(deposit);
	}

	public DepositDto getDepositById(Long depositId) {

		log.debug("REST request to get deposit : {}", depositId);
		Deposit deposit = depositRepository.findById(depositId).orElseThrow(
				() -> new NotFoundException("No deposit found for Id : " +depositId));
		return depositMapper.depositToDepositDto(deposit);
	}

	@Override
	public DepositDto updateDeposits(Long depositId, DepositDto depositDto, Authentication authentication) {
		Deposit deposit = depositRepository.findById(depositId).orElseThrow(
				() -> new NotFoundException("No Deposit found for Id : " +depositId));
		depositDto.setId(deposit.getId());
		Deposit deposits = depositMapper.depositDtoToDeposit(depositDto);			
		deposits.setMember(deposit.getMember());
		deposits.setDepositAccount(deposit.getDepositAccount());
		return depositMapper.depositToDepositDto(depositRepository.save(deposits));
		
	}

	Double amt;
	public Double calculateInterest(Long depositId, Date closureDate) {

		Deposit deposit = depositRepository.findById(depositId).orElseThrow(
				() -> new NotFoundException("No Deposit found for Id : " +depositId));
		
		if(deposit.getRecurringAmount()==0) {
		Double depositAmt = deposit.getDepositAmount();
		Double roi = deposit.getDepositAccount().getInterest();
		roi = roi - 2;// Need to take the value 2 from society configuration
		log.info("roi" + roi);
		Date depositDate = deposit.getDepositDate();
		log.info("depositDate" + depositDate);

		long currentTime = closureDate.getTime();
		long startTime = depositDate.getTime();
		long diffTillDate = currentTime - startTime;
		long tillDateDuration = (int) (diffTillDate / (24 * 60 * 60 * 1000));
		if(tillDateDuration==0)
			amt = 0.0;
		else
			amt=(depositAmt * (roi / 100 /365)) * tillDateDuration;
	}
		else {
			Double recurringAmt=0.0;
			Set<DepositLedger> ledgerSet = deposit.getDepositLedger();
			for(DepositLedger ledger:ledgerSet){
				recurringAmt = ledger.getBalanceAmount();
			}

			Double roi = deposit.getDepositAccount().getInterest();
			roi = roi - 2;// Need to take the value 2 from society configuration
			log.info("roi" + roi);
			Date depositDate = deposit.getDepositDate();
			log.info("depositDate" + depositDate);

			long currentTime = closureDate.getTime();
			long startTime = depositDate.getTime();
			long diffTillDate = currentTime - startTime;
			long tillDateDuration = (int) (diffTillDate / (24 * 60 * 60 * 1000));
			if(tillDateDuration==0)
				amt = 0.0;
			else
				amt=(recurringAmt * (roi / 100 /365)) * tillDateDuration;
		}
		return amt;

	}

	public DepositInterestCalculation calculateInterest(Long depositId) {

		DepositInterestCalculation depositInterestCalculation = new DepositInterestCalculation();
		Deposit deposit = depositRepository.findById(depositId).orElseThrow(
				() -> new NotFoundException("No Deposit found for Id : " +depositId));
		depositInterestCalculation.setDepositId(depositId);

		Double depositAmt = deposit.getDepositAmount();
		Date currentDate = new Date();
		Date matureDate = deposit.getMaturityDate();
		Double roi = deposit.getDepositAccount().getInterest();
		Integer duration = deposit.getDepositAccount().getFdDurationDays();
		Double perdayinterest = (depositAmt * roi / 100 / 365);
		Double maturityInterestAmt = perdayinterest * duration;
		Double maturityAmt = depositAmt + maturityInterestAmt;
		depositInterestCalculation.setDepositAmount(depositAmt);
		depositInterestCalculation.setMaturityDate(matureDate);
		depositInterestCalculation.setInterest(maturityInterestAmt);
		depositInterestCalculation.setMaturityAmount(maturityAmt);

		if(currentDate.compareTo(matureDate) > 0  && deposit.getReinvestmentStatus()==false){ 
			    long currentTime = currentDate.getTime();
				long startTime = matureDate.getTime();
				long diffTillDate = currentTime - startTime;
				long tillDateDuration = (int) (diffTillDate / (24 * 60 * 60 * 1000));
				List<DepositAccount> depositAccountList = depositAccountRepository.findByDepositTypeAccountType(DepositConstants.DEPOSIT_ACC_TYPE_SAVINGS);
				DepositAccount  depositAccount=depositAccountList.get(0);
				double savingInterestRate = depositAccount.getInterest();
				double additionalInterest = (depositAmt*savingInterestRate/100/tillDateDuration);			
			depositInterestCalculation.setAdditionalInterest(additionalInterest);
		}
		return depositInterestCalculation;
	}

	@Override
	public DepositDto closeAccount(Long depositId) {
	  
	  Deposit deposit =depositRepository.findById(depositId).orElseThrow(
			  () -> new NotFoundException("No Deposit found for Id : " +depositId));
	  double totalMaturityAmount=0;
	  if(deposit.getRecurringAmount()==0) {
	  	DepositInterestCalculation depositInterestCalculation=calculateInterest(depositId);
	  	totalMaturityAmount=deposit.getMaturityAmount()+depositInterestCalculation.getAdditionalInterest();

		deposit.setInterestEarned(depositInterestCalculation.getInterest() + depositInterestCalculation.getAdditionalInterest());
	  }else {
		  totalMaturityAmount=deposit.getMaturityAmount();
	  }
	  deposit.setDepositStatus(DepositConstants.DEPOSIT_ACC_CLOSE);
	  deposit.setDepositClosingDate(new Date());
	  deposit.setMaturityAmount(totalMaturityAmount);

	  depositRepository.save(deposit);
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
		if(deposit.getDepositAccruals() !=null && deposit.getDepositAccruals().size() >0) {
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
				.dayBookCreated(false)
				.build();
		return depositLedgerService.debitDeposit(depositLedgerDto);
	}

	@Override
	public DepositDto renewDeposit(Long depositId) {
		Deposit deposit01 = depositRepository.findById(depositId).orElseThrow(
				() -> new NotFoundException("No Deposit found for Id : " +depositId));
		Date currentDate = new Date();
		Double newDepositAmt = deposit01.getMaturityAmount();
		Date matureDate=deposit01.getMaturityDate();

		while(currentDate.compareTo(matureDate) > 0 ) {
			
			Deposit newDeposit= new Deposit(); 	
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(matureDate); 
			cal.add(Calendar.DATE, 1);
			Date newDepositDate = cal.getTime();

			newDeposit.setDepositDate(newDepositDate);//maturity date set to new deposit date
						
			Date matureDate1=newDeposit.getDepositDate();
			//
			Calendar cal1 = Calendar.getInstance(); 
			cal1.setTime(matureDate1); 
			cal1.add(Calendar.DATE,deposit01.getDepositAccount().getFdDurationDays());
			Date newMaturityDate = cal1.getTime();
			
			newDeposit.setMaturityDate(newMaturityDate);

			newDeposit.setDepositAmount(newDepositAmt);
			newDeposit.setReceiptNo(deposit01.getReceiptNo());
			newDeposit.setAccountNo(deposit01.getAccountNo());
			newDeposit.setDepositAccount(deposit01.getDepositAccount());
			newDeposit.setMember(deposit01.getMember());
			newDeposit.setReinvestmentStatus(true);
			newDeposit.setDepositStatus(DepositConstants.DEPOSIT_ACC_OPEN);//
			newDeposit = depositRepository.save(newDeposit);

			DepositInterestCalculation c= calculateInterest( newDeposit.getId());
			newDeposit.setMaturityAmount(c.getMaturityAmount());//set new maturity amount
			
			newDeposit =   depositRepository.save(newDeposit);
			deposit01.setDepositStatus(DepositConstants.DEPOSIT_ACC_CLOSE);
			deposit01.setDepositClosingDate(deposit01.getMaturityDate());
			deposit01 = depositRepository.save(deposit01);
			
			
			matureDate =newDeposit.getMaturityDate();//
			newDepositAmt = newDeposit.getMaturityAmount();
			
			deposit01 = newDeposit;
		}
		return depositMapper.depositToDepositDto(deposit01);

	}
	
	
	private Double calculateRDMaturityAmt(DepositAccount depositAccount,Deposit deposit ){

		float p = deposit.getRecurringAmount().floatValue();
		float r = (float) (depositAccount.getInterest()/100);
		int n = depositAccount.getRdDurationMonths();
		if(n>=12) {
			n=4;
		}else {
			n= n/3;
		}
		float t = 12;
		float rt = depositAccount.getRdDurationMonths()/deposit.getDepositFrequency();

		float arg1 = (1 + r/n);
		float arg2 = n*rt/t;
		double amt =0.0;
		double finalAmt=0.0;
		for (; rt>0; ){
			double temp1 = Math.pow(arg1,arg2);
			amt = Math.round((p * temp1)*100.0)/100.0;
			finalAmt = finalAmt+amt;
		
			rt = rt - 1;
			arg2 = n*rt/t;
		}
		return finalAmt;
	}

	@Override
	public double getFDAmount() {
		double fdAmount=depositRepository.getFDAmount();
		return fdAmount;
	}

	@Override
	public double getSavingAmount() {
		List<Long> savingList=depositRepository.getSavingAmount();
	
		
		double sum=0;
		for(int j=0;j<savingList.size();j++) {
			Optional<SavingAccount> saving=savingAccountRepository.findById(savingList.get(j));
			
			DepositLedger depositLedger=depositLedgerRepository.findByLastRecord(saving.get().getAccountNo());
			sum=sum+depositLedger.getBalanceAmount();			
		}
		
		return sum;
	}

	@Override
	public List<DepositDto> getDepositByMemberId(Long memberId) {
		List<Deposit> depositList=depositRepository.findOpenDepositsByMemberId(memberId);
		List<DepositDto>depositDtoList=depositMapper.toDtoList(depositList);
		return depositDtoList;
	}

	@Override
	public double getFdAccountCount() {
		double accountCount=depositRepository.findFixedAccountCount();
		return accountCount;
	}

	@Override
	public double getRdAccountCount() {
		double rdAccountCount=depositRepository.findRecurringAccountCount();
		return rdAccountCount;
	}

	@Override
	public DepositPageList listAllFdDeposits(Pageable pageable) {
		log.debug("Request to get deposits : {}");
		Page<Deposit> depositPage;
		depositPage = depositRepository.findFdByisDeleted(pageable, false);
		return new DepositPageList(depositPage
										.getContent()
										.stream()
										.map(depositMapper::depositToDepositDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(depositPage.getPageable().getPageNumber(),
											depositPage.getPageable().getPageSize()),
											depositPage.getTotalElements());
	}

	@Override
	public DepositPageList listAllRdDeposits(Pageable pageable) {
		log.debug("Request to get deposits : {}");
		Page<Deposit> depositPage;
		depositPage = depositRepository.findRdByisDeleted(pageable, false);
		return new DepositPageList(depositPage
										.getContent()
										.stream()
										.map(depositMapper::depositToDepositDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(depositPage.getPageable().getPageNumber(),
											depositPage.getPageable().getPageSize()),
											depositPage.getTotalElements());
	}
}
