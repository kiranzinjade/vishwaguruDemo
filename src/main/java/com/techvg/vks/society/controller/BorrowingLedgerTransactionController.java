package com.techvg.vks.society.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.society.model.BorrowingLedgerTransactionDto;
import com.techvg.vks.society.service.BorrowingLedgerTransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/borrowingLedgerTransaction")
public class BorrowingLedgerTransactionController {
	
	private final BorrowingLedgerTransactionService  borrowingLedgerTransactionService;

	
	  @PostMapping(path= {"/{transactionCriteria}"})
	  public ResponseEntity<BorrowingLedgerTransactionDto> addTransactionDetails( @Validated @RequestBody BorrowingLedgerTransactionDto borrowingLedgerTransactionDto,@PathVariable("transactionCriteria") String transactionCriteria, Authentication authentication) {
	  System.out.println("in controller ");
		  log.debug("REST request to save Purchase Register : {}",borrowingLedgerTransactionDto); //return new
	  return new ResponseEntity<>(borrowingLedgerTransactionService.addTransactionDetails(
	  borrowingLedgerTransactionDto, transactionCriteria),HttpStatus.CREATED);
	  }
	 
	  @GetMapping({ "/loanAmount" })//dashboard borrowing total loan Amount
		public double getTotalLoanAmount() {
			log.debug("REST request to get borrowing ledger : {}");
			return borrowingLedgerTransactionService.getTotalLoanAmount();
		}
	  
	  @GetMapping({ "/balanceAmount" })//dashboard borrowing total loan Amount
		public double getBalanceLoanAmount() {
			log.debug("REST request to get borrowing ledger : {}");
			return borrowingLedgerTransactionService.getBalanceLoanAmount();
		}

}
