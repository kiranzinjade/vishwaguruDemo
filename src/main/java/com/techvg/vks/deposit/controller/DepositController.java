package com.techvg.vks.deposit.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.deposit.domain.DepositInterestCalculation;
import com.techvg.vks.deposit.model.DepositDto;
import com.techvg.vks.deposit.model.DepositLedgerDto;
import com.techvg.vks.deposit.model.DepositPageList;
import com.techvg.vks.deposit.service.DepositService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/deposit")
public class DepositController {

	private final DepositService depositService;

	@PostMapping(path = { "/{memberId}/{depositAccountId}" })
	public ResponseEntity<DepositDto> addDeposit(@PathVariable("memberId") Long memberId,@PathVariable("depositAccountId") Long depositAccountId, @Validated  @RequestBody DepositDto depositDto,Authentication authentication) {
		log.debug("REST request to save deposits : {}", depositDto);
		return new ResponseEntity<>(depositService.addNewDeposit(depositDto, memberId, depositAccountId, authentication), HttpStatus.OK);
	}    

	@GetMapping("/fdList")
	public ResponseEntity<DepositPageList> listAllFdDeposits(Pageable pageable) {
		log.debug("REST request to get a all records of deposits :");
		DepositPageList depositPageList = depositService.listAllFdDeposits(pageable);
		return new ResponseEntity<>(depositPageList, HttpStatus.OK);
	}
	
	@GetMapping("/rdList")
	public ResponseEntity<DepositPageList> listAllRdDeposits(Pageable pageable) {
		log.debug("REST request to get a all records of deposits :");
		DepositPageList depositPageList = depositService.listAllRdDeposits(pageable);
		return new ResponseEntity<>(depositPageList, HttpStatus.OK);
	}
	

	@DeleteMapping(path = { "/{depositId}" }) 
	public ResponseEntity<DepositDto> deleteDepositById(@PathVariable("depositId") Long depositId) {
		log.debug("REST request to delete deposit : {}", depositId);
		return new ResponseEntity<>(depositService.deleteDepositById(depositId), HttpStatus.OK);
	}

	@PutMapping(path = { "/{depositId}" })
	public ResponseEntity<DepositDto> updateDeposits(@PathVariable("depositId") Long depositId, @Validated @RequestBody DepositDto depositDto, Authentication authentication) {
		log.info("Received Id is"+depositId);
		log.info("REST request to update deposit : {}", depositDto);
		return new ResponseEntity<>(depositService.updateDeposits(depositId, depositDto, authentication), HttpStatus.OK);
	}

	@GetMapping({ "/{depositId}" })
	public ResponseEntity<DepositDto> getDepositById(@PathVariable("depositId") Long depositId) {
		log.debug("REST request to get deposit : {}", depositId);
		return new ResponseEntity<>(depositService.getDepositById(depositId), HttpStatus.OK);
	}
    

	
	@GetMapping({ "/FD/{depositId}" })//used only for testing result
	public ResponseEntity<DepositInterestCalculation> calculateInterest(@PathVariable("depositId") Long depositId) {
		log.debug("REST request to get deposit : {}", depositId);
		return new  ResponseEntity<>(depositService.calculateInterest(depositId),HttpStatus.OK);
	}
   
	
	  @GetMapping(path={ "/closeaccount/{depositId}" })
	  public ResponseEntity<DepositDto> closeAccount(@PathVariable("depositId") Long depositId) {
	log.debug("REST request to get deposit : {}", depositId);
	return  new ResponseEntity<>(depositService.closeAccount(depositId), HttpStatus.OK); }
	  
	  
	  @PutMapping(path = { "renew/{depositId}" })
      public ResponseEntity<DepositDto> renewDeposit(@PathVariable("depositId") Long depositId) {
			log.info("Received Id is"+depositId);
			return new ResponseEntity<>(depositService.renewDeposit(depositId), HttpStatus.OK);
		}
	 
	  @GetMapping({ "/FDAmount" })//dashboard FD Amount
		public double getFDAmount() {
			log.debug("REST request to get deposit : {}");
			return depositService.getFDAmount();
		}
	  
	  @GetMapping({ "/SavingAmount" })//dashboard Saving Amount
		public double getSavingAmount() {
			log.debug("REST request to get deposit : {}");
			return depositService.getSavingAmount();
		}
	  
	  @GetMapping({ "/list/{memberId}" })
		public ResponseEntity<List<DepositDto>> getDepositByMemberId(@PathVariable("memberId") Long memberId) {
			log.debug("REST request to get deposit : {}", memberId);
			return new ResponseEntity<>(depositService.getDepositByMemberId(memberId), HttpStatus.OK);
		}
	  
	  @GetMapping({ "/fdAccountCount" })//fd Account count
		public double getFdAccountCount() {
			log.debug("REST request to get deposit : {}");
			return depositService.getFdAccountCount();
		}
	  
	  @GetMapping({ "/rdAccountCount" })//rd Account count
		public double getRdAccountCount() {
			log.debug("REST request to get deposit : {}");
			return depositService.getRdAccountCount();
		}
    
}
