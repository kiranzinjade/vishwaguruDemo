package com.techvg.vks.trading.reports.SundryCreditorReport;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
@RequestMapping("/api/sundryCreditorReport")
public class SundryCreditorReportController {
	

	private final SundryCreditorReportService sundryCreditorReportService ;

	@GetMapping({ "/{CreditorId}" })
	  public byte[] generateSundryCreditorReport(@PathVariable("CreditorId") Long CreditorId) {
	  log.debug("REST request to get Transaction Date : {}", CreditorId);
	  return sundryCreditorReportService.generateSundryCreditorReport(CreditorId); }
	 
}
