package com.techvg.vks.deposit.reports.AccountsOpenedClosed;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/openCloseAccount")
public class AccountsOpenedClosedReportController {

	private final AccountsOpenedClosedReportService accountsOpenedClosedReportService;
	@GetMapping({ "/{accountType}" })
	public ResponseEntity<?> getAccountListReport(@PathVariable("accountType") String accountType)	{ 
		return accountsOpenedClosedReportService.getAccountListReport(accountType);
	}
}
