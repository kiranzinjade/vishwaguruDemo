package com.techvg.vks.loan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.loan.model.LoanDetailsDto;
import com.techvg.vks.loan.model.LoanClosureDto;
import com.techvg.vks.loan.service.LoanClosureService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/loanclosure")
public class LoanClosureController {
	
	private final LoanClosureService loanClosureService;
	
	@GetMapping(path={ "{loanId}" })
	public ResponseEntity<LoanClosureDto> getLoanClosureAmt(@PathVariable("loanId") Long loanId) {
		return new ResponseEntity<LoanClosureDto>(loanClosureService.getLoanClosureDetails(loanId), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<LoanDetailsDto> closeLoan( @RequestBody LoanClosureDto loanClosureDto) {
		
		return new ResponseEntity<LoanDetailsDto>(loanClosureService.closeLoan(loanClosureDto), HttpStatus.OK);
		
	}
	

}
