package com.techvg.vks.accounts.reports.TradingBalanceSheet;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.accounts.model.LedgerAccountsDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/api/tradingBalanceSheet")
public class TradingBalanceSheetController {

	private final TradingBalanceSheetService tradingBalanceSheetService;
	
	@GetMapping("/expenseList")
	public List<LedgerAccountsDto> listTradingExpense(){
		return tradingBalanceSheetService.listTradingExpense();
	}
	
	@GetMapping("/incomeList")
	public List<LedgerAccountsDto> listTradingIncome(){
		return tradingBalanceSheetService.listTradingIncome();
	}
}
