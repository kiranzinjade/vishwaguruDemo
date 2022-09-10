package com.techvg.vks.accounts.reports.TradingBalanceSheet;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techvg.vks.accounts.model.LedgerAccountsDto;

@Service
public interface TradingBalanceSheetService {

	List<LedgerAccountsDto> listTradingExpense();

	List<LedgerAccountsDto> listTradingIncome();

}
