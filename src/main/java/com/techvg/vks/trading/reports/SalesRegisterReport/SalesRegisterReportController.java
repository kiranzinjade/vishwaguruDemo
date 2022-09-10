package com.techvg.vks.trading.reports.SalesRegisterReport;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.trading.reports.PurchaseRegisterReport.PurchaseRegisterReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/salesReport")
public class SalesRegisterReportController {
	
private final SalesRegisterReportService  salesRegisterReportService;
	


	@GetMapping( "/report/{salesOrderId}")
	public byte[] getSalesRegisterReport(@PathVariable("salesOrderId")Long salesOrderId) {
	return salesRegisterReportService.getSalesRegisterReport(salesOrderId);
}
}
