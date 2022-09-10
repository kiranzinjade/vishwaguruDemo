package com.techvg.vks.deposit.controller;

import com.techvg.vks.deposit.model.DepositDto;
import com.techvg.vks.deposit.model.DepositLedgerDto;
import com.techvg.vks.deposit.model.SavingAccountDto;
import com.techvg.vks.deposit.model.SavingAccountPageList;
import com.techvg.vks.deposit.service.SavingAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/deposit/savingaccount")
public class SavingAccountController {
	
	private final  SavingAccountService savingAccountService;
	
	@PostMapping(path = { "/{memberId}" })
	public ResponseEntity<SavingAccountDto> addSavingAccountDetails(@PathVariable("memberId") Long memberId, @RequestBody SavingAccountDto savingAccountDto) {
		log.debug("REST request to save saving account details : {}", savingAccountDto);
		return new ResponseEntity<>( savingAccountService.addSavingAccountDetails(memberId,savingAccountDto), HttpStatus.CREATED);
	}
	
	
	@PutMapping(path = { "/{savingAccountId}" })
	public ResponseEntity<SavingAccountDto> updateSavingAccountDetails(@PathVariable("savingAccountId") Long savingAccountId, @RequestBody SavingAccountDto savingAccountDto) {
		log.debug("REST request to save saving account details : {}", savingAccountDto);
		return new ResponseEntity<>( savingAccountService.updateSavingAccountDetails(savingAccountId,savingAccountDto), HttpStatus.OK);
	}
	
	@GetMapping({ "/{id}" })
	public ResponseEntity<SavingAccountDto> getSavingAccountDetails(@PathVariable("id") Long id) {
		log.debug("REST request to get SavingAccount Details : {}", id);
		return new ResponseEntity<>(savingAccountService.getSavingAccountDetailsById(id), HttpStatus.OK);
	}
	
	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<SavingAccountDto> deleteSavingAccountDetailsById(@PathVariable("id") Long id) {
		log.debug("REST request to delete SavingAccount Details : {}", id);
		return new ResponseEntity<>(savingAccountService.deleteSavingAccountDetailsById(id), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<SavingAccountPageList> listAllSavingAccount(Pageable pageable) {

		log.debug("REST request to get a all records of saving account");
		SavingAccountPageList savingAccountList = savingAccountService.listSavingAccount(pageable);
		return new ResponseEntity<>(savingAccountList, HttpStatus.OK);
	}
	
	
	@GetMapping({ "/closeaccount/{memberId}/{savingAccountId}" })
	public ResponseEntity<SavingAccountDto> closeSavingAccount(@PathVariable("memberId") Long memberId, @PathVariable("savingAccountId")Long savingAccountId) {
		log.debug("REST request to get SavingAccount Details : {}", memberId);
		return new ResponseEntity<>(savingAccountService.closeSavingAccount(memberId,savingAccountId), HttpStatus.OK);
	}
	
	@GetMapping("/get/{accountNo}/{year}")
	public ResponseEntity<DepositLedgerDto>  calculateInterest(@PathVariable("accountNo") Long accountNo,@PathVariable("year")Integer year) {
		return  new ResponseEntity<>(savingAccountService.postSavingInterest(accountNo,year), HttpStatus.OK);
	}
	
	 @GetMapping({ "/saving/{memberId}" })
		public ResponseEntity<SavingAccountDto> getSavingByMemberId(@PathVariable("memberId") Long memberId) {
			log.debug("REST request to get saving : {}", memberId);
			return new ResponseEntity<>(savingAccountService.getSavingByMemberId(memberId), HttpStatus.OK);
		}
	
	 @GetMapping({ "/accountCount" })//saving Account count
		public double getSavingAccountCount() {
			log.debug("REST request to get saving : {}");
			return savingAccountService.getSavingAccountCount();
		}
	
}
