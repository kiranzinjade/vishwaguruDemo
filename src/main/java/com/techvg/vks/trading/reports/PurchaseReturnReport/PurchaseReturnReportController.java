package com.techvg.vks.trading.reports.PurchaseReturnReport;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.trading.reports.SalesReturnReport.SalesReturnReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/purchaseReturnReport")
public class PurchaseReturnReportController {
	
private final PurchaseReturnReportService  purchaseReturnReportService;
	
	@GetMapping()
	public byte[] getPurchaseReturnReport() {
		return purchaseReturnReportService.getPurchaseReturnReport();
	}
}
