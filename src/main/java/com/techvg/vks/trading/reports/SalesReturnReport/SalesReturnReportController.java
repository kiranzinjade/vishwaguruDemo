package com.techvg.vks.trading.reports.SalesReturnReport;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.trading.reports.PurchaseRegisterReport.PurchaseRegisterReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/salesReturnReport")
public class SalesReturnReportController {

private final SalesReturnReportService  salesReturnReportService;
	
	@GetMapping()
	public byte[] getSalesReturnReport() {
		return salesReturnReportService.getSalesReturnReport();
	}
}
