package com.techvg.vks.accounts.reports.tradingPofitLossReport;

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
public class TradingProfitLossReportServiceImpl implements TradingProfitLossReportService{
	
	private final LedgerAccountsRepository ledgerAccountsRepository;
	private final LedgerAccountMapper ledgerAccountMapper;
	
	@Override
	public List<LedgerAccountsDto> listTradingExpense() {
		List<LedgerAccounts> ledgerList=ledgerAccountsRepository.findTradingByAccountType(AccountConstants.EXPENSE);
		return ledgerAccountMapper.domainToDtoList(ledgerList);
	}

	@Override
	public List<LedgerAccountsDto> listTradingIncome() {
		List<LedgerAccounts> ledgerList=ledgerAccountsRepository.findTradingByAccountType(AccountConstants.INCOME);
		return ledgerAccountMapper.domainToDtoList(ledgerList);
	}

	@Override
	public LedgerAccountsDto getPurchaseReturn() {
		LedgerAccounts ledger = ledgerAccountsRepository.findRecordForPurchaseReturn();
		return ledgerAccountMapper.toLedgerAccountsDto(ledger);
	}

	@Override
	public LedgerAccountsDto getSalesReturn() {
		LedgerAccounts ledger = ledgerAccountsRepository.findRecordForSalesReturn();
		return ledgerAccountMapper.toLedgerAccountsDto(ledger);
	}

	@Override
	public LedgerAccountsDto getNonCreditActivities() {
		LedgerAccounts ledger = ledgerAccountsRepository.findRecordForNonCreditActivities();
		return ledgerAccountMapper.toLedgerAccountsDto(ledger);
	}

}
