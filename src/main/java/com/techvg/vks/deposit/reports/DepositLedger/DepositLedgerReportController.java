package com.techvg.vks.deposit.reports.DepositLedger;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/depositLedgerReport")
public class DepositLedgerReportController {

	private final DepositLedgerReportService depositLedgerReportService;
	
	@GetMapping({ "/{id}/{accountNo}" })
	public byte[] getDepositLedgerReport(@PathVariable("id") Long id,@PathVariable("accountNo") Long accountNo)	{ 
		return depositLedgerReportService.getDepositLedgerReport(id,accountNo);
	}

}
