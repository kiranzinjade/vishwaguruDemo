package com.techvg.vks.accounts.reports.TrialBalanceReport;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.accounts.model.GeneralLedgerDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/api/trialBalancereport")
public class TrialBalanceReportController {

	private final TrialBalanceReportService trialBalanceReportService;
	
	@GetMapping({"/assetList/{transMonth}"})
	public List<GeneralLedgerDto> getAssetAndExpenseList(@PathVariable("transMonth")Integer transMonth) 
	{ 
		return trialBalanceReportService.getAssetAndExpenseList(transMonth);
	}
	
	@GetMapping({"/liabilityList/{transMonth}"})
	public List<GeneralLedgerDto> getLiabilityAndIncomeList(@PathVariable("transMonth")Integer transMonth) 
	{ 
		return trialBalanceReportService.getLiabilityAndIncomeList(transMonth);
	}
}
