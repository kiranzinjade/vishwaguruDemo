package com.techvg.vks.accounts.reports.ProfitAndLossReport;

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
@RequestMapping("/api/profitandloss")
public class ProfitAndLossController {

	private final ProfitAndLossService profitAndLossService;
	
	@GetMapping("/expenselist")
	public List<LedgerAccountsDto> listExpense(){
		return profitAndLossService.listExpense();
	}
	
	@GetMapping("/incomelist")
	public List<LedgerAccountsDto> listIncome(){
		return profitAndLossService.listIncome();
	}
}
