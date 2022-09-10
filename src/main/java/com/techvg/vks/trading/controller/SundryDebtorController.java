package com.techvg.vks.trading.controller;

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

import com.techvg.vks.trading.model.SundryCreditorDto;
import com.techvg.vks.trading.model.SundryCreditorPageList;
import com.techvg.vks.trading.model.SundryDebtorDto;
import com.techvg.vks.trading.model.SundryDebtorPageList;
import com.techvg.vks.trading.service.SundryDebtorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/sundryDebitor") 
public class SundryDebtorController {

	private final SundryDebtorService sundryDebtorService;
	@PostMapping
	public ResponseEntity<SundryDebtorDto> addSundryDebitor(@Validated  @RequestBody SundryDebtorDto sundryDebtorDto, Authentication authentication) {
		log.debug("REST request to save Debitor : {}", sundryDebtorDto);
		return new ResponseEntity<SundryDebtorDto>(sundryDebtorService.addSundryDebitor(sundryDebtorDto, authentication),
				HttpStatus.CREATED);
	}
	@PutMapping(path = { "/{sundryDebtorId}" })
	public ResponseEntity<SundryDebtorDto> updateSundry(@PathVariable("sundryDebtorId") Long sundryDebtorId,
			@Validated @RequestBody SundryDebtorDto sundryDebtorDto) {
		log.debug("REST request to update assets : {}", sundryDebtorDto);
		return new ResponseEntity<SundryDebtorDto>(sundryDebtorService.updateSundry(sundryDebtorId, sundryDebtorDto), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<SundryDebtorPageList> listAllDebtors(Pageable pageable) {

		log.debug("REST request to get a all records of debtors");
		SundryDebtorPageList debtorsList = sundryDebtorService.listAllDebtors(pageable);
		return new ResponseEntity<>(debtorsList, HttpStatus.OK);
	}
	
	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<SundryDebtorDto> deleteCrditorById(@PathVariable("id") long id) {
		log.debug("REST request to delete Debtors : {}", id);
		return new ResponseEntity<SundryDebtorDto>(sundryDebtorService.deleteDebtorsById(id), HttpStatus.OK);
	}
	
	@GetMapping({ "/{id}" })
	public ResponseEntity<SundryDebtorDto> getDebtor(@PathVariable("id") long id) {
		log.debug("REST request to get Creditor : {}", id);
		return new ResponseEntity<SundryDebtorDto>(sundryDebtorService.getDebtorById(id), HttpStatus.OK);
	}

}
