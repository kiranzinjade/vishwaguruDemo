package com.techvg.vks.deposit.reports.SavingsAccountLedger;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/savingAccountReport")
public class SavingAccountReportController {
	
	private final SavingAccountReportService savingAccountReportService;
	@GetMapping({ "/{accountNo}" })
	public ResponseEntity<?> getSavingAccountListReport(@PathVariable("accountNo") Long accountNo)	{ 
		return savingAccountReportService.getSavingAccountListReport(accountNo);
	}

}
