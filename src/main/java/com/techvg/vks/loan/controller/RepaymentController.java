package com.techvg.vks.loan.controller;

import com.techvg.vks.accounts.model.VouchersDto;
import com.techvg.vks.loan.model.RepaymentDto;
import com.techvg.vks.loan.service.RepaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/loan/repayment")

public class RepaymentController {
	
	private final RepaymentService repaymentService ;

	@PostMapping(path = { "/stloanpayment" })
	public ResponseEntity<VouchersDto> acceptSTLoanRepayment(@RequestBody RepaymentDto repaymentDto) {
		return new ResponseEntity<>( repaymentService.acceptSTLoanRepayment(repaymentDto), HttpStatus.CREATED);
	}

	@PostMapping(path = { "/stloanpaymentpreview" })
	public ResponseEntity<RepaymentDto> previewSTLoanRepayment(@RequestBody RepaymentDto repaymentDto) {
		return new ResponseEntity<>( repaymentService.previewSTLoanRepayment(repaymentDto), HttpStatus.CREATED);
	}
	
	@GetMapping({ "/{loanId}" })
	public ResponseEntity<List<RepaymentDto>> getLoanRepayment(@PathVariable("loanId") Long loanId) {
		log.debug("REST request to get Loan  deatils : {}", loanId);
		return new ResponseEntity<>(repaymentService.getLoanRepaymentById(loanId), HttpStatus.OK);
	}
	
	@GetMapping({ "/lastrepayment/{loanId}" })
	public ResponseEntity<RepaymentDto> getLastRepayment(@PathVariable("loanId") Long loanId) {
		log.debug("REST request to get Loan  deatils : {}", loanId);
		return new ResponseEntity<>(repaymentService.getLastRepaymentById(loanId), HttpStatus.OK);
	}
	
	
}
