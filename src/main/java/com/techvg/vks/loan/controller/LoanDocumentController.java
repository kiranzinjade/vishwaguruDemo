package com.techvg.vks.loan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.loan.model.LoanDocumentDto;
import com.techvg.vks.loan.service.LoanDocumentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/loan")
public class LoanDocumentController {
	
	private final LoanDocumentService loanDocumentService;

	@PostMapping
	public ResponseEntity<LoanDocumentDto>addLoanDocument(LoanDocumentDto loanDocumentDto)
	{
		log.debug("REST request to save Meeting details : {}",loanDocumentDto);
		return new ResponseEntity<>(loanDocumentService.addLoanDocument(loanDocumentDto), HttpStatus.CREATED);
	}
	

}
