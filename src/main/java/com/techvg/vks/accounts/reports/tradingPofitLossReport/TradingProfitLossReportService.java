package com.techvg.vks.accounts.reports.tradingPofitLossReport;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techvg.vks.accounts.model.LedgerAccountsDto;

@Service
public interface TradingProfitLossReportService {

	List<LedgerAccountsDto> listTradingExpense();

	List<LedgerAccountsDto> listTradingIncome();

	LedgerAccountsDto getPurchaseReturn();

	LedgerAccountsDto getSalesReturn();

	LedgerAccountsDto getNonCreditActivities();

}
