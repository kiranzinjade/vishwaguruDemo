package com.techvg.vks.accounts.controller;

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

import com.techvg.vks.accounts.model.ProfitDistributionLedgerDto;
import com.techvg.vks.accounts.model.ProfitDistributionLedgerPageList;
import com.techvg.vks.accounts.service.ProfitDistributionLedgerService;
import com.techvg.vks.society.controller.BorrowingLedgerController;
import com.techvg.vks.society.model.BorrowingLedgerDto;
import com.techvg.vks.society.model.BorrowingLedgerPageList;
import com.techvg.vks.society.service.BorrowingLedgerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/ledgerList")
public class ProfitDistributionLedgerController {

	private final ProfitDistributionLedgerService  profitDistributionLedgerService;

	@PostMapping
	public ResponseEntity<ProfitDistributionLedgerDto> addLedgerDetails( @RequestBody ProfitDistributionLedgerDto profitDistributionLedgerDto,  Authentication authentication) {
		log.debug("REST request to save Ledger List Details : {}", profitDistributionLedgerDto);
		return new ResponseEntity<>(profitDistributionLedgerService.addLedgerDetails(profitDistributionLedgerDto,authentication) , HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<ProfitDistributionLedgerPageList> listAllLedgerDetails(Pageable pageable) {

		log.debug("REST request to get a all records of Ledger List");
		ProfitDistributionLedgerPageList ledgerList = profitDistributionLedgerService.listLedger(pageable);
		return new ResponseEntity<>(ledgerList, HttpStatus.OK);
	}
	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<ProfitDistributionLedgerDto> deleteLedgerById(@PathVariable("id") Long id) {
		log.debug("REST request to delete Ledger List : {}", id);
		return new ResponseEntity<>(profitDistributionLedgerService.deleteLedgerById(id), HttpStatus.OK);
	}
	
	@PutMapping(path = { "/{id}" })
	public ResponseEntity<ProfitDistributionLedgerDto> updateLedgerList(@PathVariable("id") Long id, @Validated @RequestBody ProfitDistributionLedgerDto profitDistributionLedgerDto) {
		log.debug("REST request to update Ledger List : {}", profitDistributionLedgerDto);
		return new ResponseEntity<>(profitDistributionLedgerService.updateLedgerList(id, profitDistributionLedgerDto), HttpStatus.OK);
	}
	
	@GetMapping({ "/{id}" })
	public ResponseEntity<ProfitDistributionLedgerDto> getLedgerListById(@PathVariable("id") Long id) {
		log.debug("REST request to get Ledger List : {}", id);
		return new ResponseEntity<>(profitDistributionLedgerService.getLedgerListById(id), HttpStatus.OK);
	}

}
