package com.techvg.vks.trading.reports.PurchaseRegisterReport;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.loan.reports.LoanDisbursementRegister.LoanDisbursementRegisterService;
import com.techvg.vks.trading.model.PurchaseOrderDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/purchaseReport")
public class PurchaseRegisterReportController {
	
private final PurchaseRegisterReportService  purchaseRegisterReportService;
	

	@GetMapping( "/report/{purchaseOrderId}")
	public byte[] getPurchaseRegisterReport(@PathVariable("purchaseOrderId")Long purchaseOrderId) {
		return purchaseRegisterReportService.getPurchaseRegisterReport(purchaseOrderId);
    }

}
