package com.techvg.vks.loan.controller;

import com.techvg.vks.loan.model.LoanDetailsDto;
import com.techvg.vks.loan.model.LoanInterestDetails;
import com.techvg.vks.loan.service.LoanForeclosureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/loanforeclosure")
public class LoanForeclosureController {
	
	private final LoanForeclosureService loanForeclosureService ;

	@GetMapping({ "shortloan/{loanId}/{foreclosureDate}" })
	public ResponseEntity<LoanInterestDetails> getForeclosureAmtForShortLoan(@PathVariable("loanId") Long loanId, @PathVariable("foreclosureDate") String foreclosureDate) {
		return new ResponseEntity<>(loanForeclosureService.getForeclosureAmtShortLoan(loanId,foreclosureDate), HttpStatus.OK);
	}
	
	
	@PutMapping(path = { "shortmidlongloan/{loanId}"})
	public ResponseEntity<LoanDetailsDto> forecloseShortMidLongLoan(@PathVariable("loanId") Long loanId,  @RequestBody LoanInterestDetails foreclosureAmt) {
		
		return new ResponseEntity<>(loanForeclosureService.forecloseShortMidLongLoan(loanId, foreclosureAmt), HttpStatus.OK);
		
	}

	@GetMapping({ "midlongloan/{loanId}/{foreclosureDate}" })
	public ResponseEntity<LoanInterestDetails> getForeclosureAmtForMidLongLoan(@PathVariable("loanId") Long loanId, @PathVariable("foreclosureDate") String foreclosureDate ) {
		return new ResponseEntity<>(loanForeclosureService.getForeclosureAmtMidLongLoan(loanId,foreclosureDate), HttpStatus.OK);
	}
	

	

}
