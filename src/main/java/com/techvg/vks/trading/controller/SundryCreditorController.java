package com.techvg.vks.trading.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.techvg.vks.society.model.AssetsDto;
import com.techvg.vks.society.model.AssetsPageList;
import com.techvg.vks.trading.model.SundryCreditorDto;
import com.techvg.vks.trading.model.SundryCreditorPageList;
import com.techvg.vks.trading.service.SundryCreditorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/sundryCreditor")
public class SundryCreditorController {
	
private final SundryCreditorService sundryCreditorService;
	
	@PostMapping
	public ResponseEntity<SundryCreditorDto> addTransactions(@Validated @RequestBody SundryCreditorDto sundryCreditorDto) {
		log.debug("REST request to update Stock : {}", sundryCreditorDto);
		return new ResponseEntity<>(sundryCreditorService.addTransactions(sundryCreditorDto), HttpStatus.OK);
	}
	@PutMapping(path = { "/{sundryCreditorId}" })
	public ResponseEntity<SundryCreditorDto> updateSundry(@PathVariable("sundryCreditorId") Long sundryCreditorId,
			@Validated @RequestBody SundryCreditorDto sundryCreditorDto) {
		log.debug("REST request to update assets : {}", sundryCreditorDto);
		return new ResponseEntity<SundryCreditorDto>(sundryCreditorService.updateSundry(sundryCreditorId, sundryCreditorDto), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<SundryCreditorPageList> listAllCreditors(Pageable pageable) {

		log.debug("REST request to get a all records of creditors");
		SundryCreditorPageList creditorList = sundryCreditorService.listAllCreditors(pageable);
		return new ResponseEntity<>(creditorList, HttpStatus.OK);
	}
	
	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<SundryCreditorDto> deleteCrditorById(@PathVariable("id") long id) {
		log.debug("REST request to delete Creditor : {}", id);
		return new ResponseEntity<SundryCreditorDto>(sundryCreditorService.deleteCreditorById(id), HttpStatus.OK);
	}
	
	@GetMapping({ "/{id}" })
	public ResponseEntity<SundryCreditorDto> getCreditor(@PathVariable("id") long id) {
		log.debug("REST request to get Creditor : {}", id);
		return new ResponseEntity<SundryCreditorDto>(sundryCreditorService.getCreditorById(id), HttpStatus.OK);
	}
	
}
