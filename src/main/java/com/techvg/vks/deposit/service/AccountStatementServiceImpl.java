package com.techvg.vks.deposit.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.techvg.vks.deposit.domain.Deposit;
import com.techvg.vks.deposit.domain.DepositLedger;
import com.techvg.vks.deposit.domain.SavingAccount;
import com.techvg.vks.deposit.mapper.DepositLedgerMapper;
import com.techvg.vks.deposit.model.DepositLedgerDto;
import com.techvg.vks.deposit.repository.DepositLedgerRepository;
import com.techvg.vks.deposit.repository.DepositRepository;
import com.techvg.vks.deposit.repository.SavingAccountRepository;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountStatementServiceImpl implements AccountStatementService {
	
	private final DepositLedgerRepository  depositLedgerRepository;
	private final DepositLedgerMapper depositLedgerMapper;
	private final DepositRepository  depositRepository;
	private final MemberRepository memberRepository;
	private final SavingAccountRepository  savingAccountRepository;
	Member member;
	
	@Override
	public List<DepositLedgerDto> generateAccountStatement(Long id, String fromDate, String toDate) {
		
		//convert string into Local date format 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		LocalDate fromdate1 = LocalDate.parse(fromDate, formatter);

		LocalDate todate2 = LocalDate.parse(toDate, formatter);

		// convert Local date into date format with hh:mm:ss
		Instant instant = fromdate1.atStartOfDay(ZoneId.systemDefault()).toInstant();
		Date fromdate4 = Date.from(instant);

		Instant instant1 = todate2.atStartOfDay(ZoneId.systemDefault()).toInstant();
		Date todate5 = Date.from(instant1);

//		Optional<Deposit> deposit=depositRepository.findByAccountNo(accountNo);
//		if(deposit.isPresent()) {
//			 member = memberRepository.findById(deposit.get().getMember().getId()).orElseThrow(() -> new NotFoundException("No member found for : " + deposit.get().getMember().getId()));
//		}
//		
//		Optional<SavingAccount>  savingAccount=savingAccountRepository.findByAccountNo(accountNo);
//		if(savingAccount.isPresent()) {
//			 member = memberRepository.findById(savingAccount.get().getMember().getId()).orElseThrow(() -> new NotFoundException("No member found for : " + savingAccount.get().getMember().getId()));
//		}
//		List<DepositLedger> depositledger=depositLedgerRepository.findByAccountNoAndDepositDate(accountNo, fromdate4, todate5);
//		List<DepositLedgerDto> deposit2=depositLedgerMapper.toDtoList(depositledger);
//		deposit2.forEach(action->{
//			action.setFullName(member.getFirstName()+" "+member.getMiddleName()+" "+member.getLastName());
//		});
		
		Optional<Deposit> deposit=depositRepository.findById(id);
//		Deposit deposit=depositRepository.findById(id).orElseThrow(() -> new NotFoundException("No deposit found for : " + id));
		if(deposit.isPresent()) {
			 member = memberRepository.findById(deposit.get().getMember().getId()).orElseThrow(() -> new NotFoundException("No member found for : " + deposit.get().getMember().getId()));
		}
		
		Optional<SavingAccount>  savingAccount=savingAccountRepository.findById(id);
//		SavingAccount savingAccount=savingAccountRepository.findById(id).orElseThrow(() -> new NotFoundException("No saving account found for : " + id));
		if(savingAccount.isPresent()) {
			 member = memberRepository.findById(savingAccount.get().getMember().getId()).orElseThrow(() -> new NotFoundException("No member found for : " + savingAccount.get().getMember().getId()));
		}
		List<DepositLedger> depositledger=depositLedgerRepository.findByAccountNoAndDepositDate(id, fromdate4, todate5);
		List<DepositLedgerDto> deposit2=depositLedgerMapper.toDtoList(depositledger);
		deposit2.forEach(action->{
			action.setFullName(member.getFirstName()+" "+member.getMiddleName()+" "+member.getLastName());
		});
		return deposit2;
	}

}
