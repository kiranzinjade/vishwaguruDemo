package com.techvg.vks.deposit.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techvg.vks.deposit.model.DepositLedgerDto;
import com.techvg.vks.deposit.model.SavingAccountDto;
import com.techvg.vks.deposit.model.SavingAccountPageList;
import com.techvg.vks.loan.model.LoanDemandDto;
import com.techvg.vks.loan.model.LoanDemandPageList;


@Service
public interface SavingAccountService {
	
	SavingAccountDto addSavingAccountDetails(Long memberId, SavingAccountDto savingAccountDto);
	
	SavingAccountDto updateSavingAccountDetails(Long savingAccountId, SavingAccountDto savingAccountDto);
	
	SavingAccountDto getSavingAccountDetailsById(Long id);
	
	SavingAccountDto deleteSavingAccountDetailsById(Long id);
	
	SavingAccountPageList listSavingAccount(Pageable pageable);

	SavingAccountDto closeSavingAccount(Long memberId, Long savingAccountId);
	  
    Double calculateInterest(Long accountNo,Integer year);

	Double calculateInterest(Long accountNo);
	
	DepositLedgerDto postSavingInterest(Long accountNo, Integer year);

	SavingAccountDto getSavingByMemberId(Long memberId);

	double getSavingAccountCount();

	 
}
