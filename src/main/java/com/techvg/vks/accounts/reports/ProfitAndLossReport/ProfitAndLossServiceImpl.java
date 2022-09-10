package com.techvg.vks.accounts.reports.ProfitAndLossReport;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techvg.vks.accounts.domain.LedgerAccounts;
import com.techvg.vks.accounts.mapper.LedgerAccountMapper;
import com.techvg.vks.accounts.model.LedgerAccountsDto;
import com.techvg.vks.accounts.repository.LedgerAccountsRepository;
import com.techvg.vks.config.AccountConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfitAndLossServiceImpl implements ProfitAndLossService{
	
	private final LedgerAccountsRepository ledgerAccountsRepository;
	private final LedgerAccountMapper ledgerAccountMapper;
	
	@Override
	public List<LedgerAccountsDto> listExpense() {
		List<LedgerAccounts> ledgerList=ledgerAccountsRepository.findPLByAccountType(AccountConstants.EXPENSE);
		return ledgerAccountMapper.domainToDtoList(ledgerList);
	}

	@Override
	public List<LedgerAccountsDto> listIncome() {
		List<LedgerAccounts> ledgerList=ledgerAccountsRepository.findPLByAccountType(AccountConstants.INCOME);
		return ledgerAccountMapper.domainToDtoList(ledgerList);
	}

}
