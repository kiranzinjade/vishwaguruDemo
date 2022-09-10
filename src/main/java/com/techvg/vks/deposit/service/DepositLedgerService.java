package com.techvg.vks.deposit.service;

import java.util.Date;
import java.util.List;

import com.techvg.vks.deposit.domain.DepositLedger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.techvg.vks.deposit.model.DepositLedgerDto;

@Service
public interface DepositLedgerService {

	DepositLedgerDto creditDepositAccount(DepositLedgerDto depositLedgerDto, Authentication authentication);

	DepositLedger creditDeposit(DepositLedgerDto depositLedgerDto);

	DepositLedger debitDeposit(DepositLedgerDto depositLedgerDto);

	DepositLedgerDto creditSavingAccount(DepositLedgerDto depositLedgerDto, Authentication authentication);

	List<DepositLedgerDto> listDepositLedger(Long id);

	double getRecurringAmount();

	DepositLedgerDto getLastRecord(Long accountNo);

	List<DepositLedger> getLedgerEntries(Date transDate);

	boolean populateDayBook(Date transDate);
//	DepositLedgerDto debitSavingAccount(DepositLedgerDto depositLedgerDto, Authentication authentication);

}
