package com.techvg.vks.deposit.controller;

import java.util.List;

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

import com.techvg.vks.accounts.model.VoucherTypeDto;
import com.techvg.vks.deposit.model.DepositLedgerDto;
import com.techvg.vks.deposit.service.DepositLedgerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/depositLedger")
public class DepositLedgerController {

	private final DepositLedgerService depositLedgerService;

	@PostMapping(path = { "deposit/credit" })
	public ResponseEntity<DepositLedgerDto> creditDepositAccount(
			@Validated @RequestBody DepositLedgerDto depositLedgerDto, Authentication authentication) {
		log.debug("REST request to save deposits : {}", depositLedgerDto);
		return new ResponseEntity<>(
				depositLedgerService.creditDepositAccount(depositLedgerDto, authentication), HttpStatus.OK);
	}

	
	@PostMapping(path = { "savingAccount/transaction" })
	public ResponseEntity<DepositLedgerDto> creditSavingAccount(
			@Validated @RequestBody DepositLedgerDto depositLedgerDto, Authentication authentication) {
		log.debug("REST request to save deposits : {}", depositLedgerDto);
		return new ResponseEntity<>(
				depositLedgerService.creditSavingAccount(depositLedgerDto, authentication), HttpStatus.OK);
	}
	 @GetMapping( { "/{id}" })
	   public ResponseEntity<List<DepositLedgerDto>> listDepositLedger(@PathVariable("id") Long id) {
	        return new ResponseEntity<>(depositLedgerService.listDepositLedger(id), HttpStatus.OK);
	    }
	 

	  @GetMapping({ "/recurringAmount" })//dashboard Recurring Amount
		public double getRecurringAmount() {
			log.debug("REST request to get deposit : {}");
			return depositLedgerService.getRecurringAmount();
		}
	  
	  @GetMapping( { "/lastRecord/{accountNo}" })
	   public ResponseEntity<DepositLedgerDto> getLastRecord(@PathVariable("accountNo") Long accountNo) {
	        return new ResponseEntity<>(depositLedgerService.getLastRecord(accountNo), HttpStatus.OK);
	    }
	 

}
