package com.techvg.vks.accounts.reports.tradingPofitLossReport;

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
@RequestMapping("/api/tradingProfitAndLoss")
public class TradingProfitLossReportController {
	
private final TradingProfitLossReportService tradingprofitAndLossService;
	
@GetMapping("/expenseList")
public List<LedgerAccountsDto> listTradingExpense(){
	return tradingprofitAndLossService.listTradingExpense();
}

@GetMapping("/incomeList")
public List<LedgerAccountsDto> listTradingIncome(){
	return tradingprofitAndLossService.listTradingIncome();
}

@GetMapping("/purchaseReturn")
public LedgerAccountsDto getPurchaseReturn(){
	return tradingprofitAndLossService.getPurchaseReturn();
}

@GetMapping("/salesReturn")
public LedgerAccountsDto getSalesReturn(){
	return tradingprofitAndLossService.getSalesReturn();
}
@GetMapping("/borrowing")
public LedgerAccountsDto getNonCreditActivities(){
	return tradingprofitAndLossService.getNonCreditActivities();
}

}
