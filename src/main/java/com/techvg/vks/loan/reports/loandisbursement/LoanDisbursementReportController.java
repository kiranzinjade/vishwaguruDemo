package com.techvg.vks.loan.reports.loandisbursement;

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
@RequestMapping("/api/disbursementlist")
public class LoanDisbursementReportController {

	private final LoanDisbursementReportService loanDisbursementService;

	@GetMapping({ "/type/{loanType}" })
	public ResponseEntity<?> getLoanDisbursementByType(@PathVariable("loanType") String loanType) {
		return loanDisbursementService.getLoanDisbursement(loanType);
	}
	
	@GetMapping({ "/id/{loanId}" })
	public ResponseEntity<?> getLoanDisbursementById(@PathVariable("loanId") Long loanId) {
		return loanDisbursementService.getLoanDisbursement(loanId);
	}

}
