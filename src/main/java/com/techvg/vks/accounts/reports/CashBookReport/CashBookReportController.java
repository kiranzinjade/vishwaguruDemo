package com.techvg.vks.accounts.reports.CashBookReport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/api/cashbookreport")
public class CashBookReportController {

	
	private final CashBookReportService cashBookReportService;
	
	@GetMapping({ "/{transDate}" })
	public byte[] generateCashBookReport(@PathVariable("transDate") String  transDate)  {
		log.debug("REST request to get Transaction Date : {}", transDate);
	return cashBookReportService.generateCashBookReport(transDate);
	}
}
