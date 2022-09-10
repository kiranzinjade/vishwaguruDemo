package com.techvg.vks.deposit.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.techvg.vks.deposit.domain.DepositInterestCalculation;
import com.techvg.vks.deposit.model.DepositDto;
import com.techvg.vks.deposit.model.DepositPageList;

public interface DepositService {

	DepositDto addNewDeposit(DepositDto depositDto, Long memberId, Long depositAccountId, Authentication authentication);

	DepositPageList listAllDeposits(Pageable pageable);

	DepositDto deleteDepositById(Long depositId);

	DepositDto getDepositById(Long depositId);

	DepositDto updateDeposits(Long depositId, DepositDto depositDto, Authentication authentication);
	
	DepositInterestCalculation calculateInterest(Long depositId);

	Double calculateInterest(Long depositId,Date closureDate);

	DepositDto closeAccount(Long depositId);
	
	DepositDto renewDeposit(Long depositId);

	double getFDAmount();

	double getSavingAmount();

	List<DepositDto> getDepositByMemberId(Long memberId);

	double getFdAccountCount();

	double getRdAccountCount();

	DepositPageList listAllFdDeposits(Pageable pageable);

	DepositPageList listAllRdDeposits(Pageable pageable);

}
