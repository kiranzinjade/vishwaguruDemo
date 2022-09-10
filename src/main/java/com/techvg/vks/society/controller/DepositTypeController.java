package com.techvg.vks.society.controller;

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

import com.techvg.vks.accounts.model.VoucherTypeDto;
import com.techvg.vks.society.model.DepositTypeDto;
import com.techvg.vks.society.model.DepositTypePageList;
import com.techvg.vks.society.model.SocietyBankDto;
import com.techvg.vks.society.service.DepositTypeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/depositType")
public class DepositTypeController {
private final DepositTypeService depositTypeService;
	
	@PostMapping
	public ResponseEntity<DepositTypeDto> addPrerequisite(@Validated  @RequestBody DepositTypeDto depositTypeDto,  Authentication authentication) {
		log.debug("REST request to save DepositType : {}", depositTypeDto);
		return new ResponseEntity<>(depositTypeService.addDepositType(depositTypeDto,authentication) , HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<DepositTypePageList> listAllDepositType(Pageable pageable) {

		log.debug("REST request to get a all records of DepositType");
		DepositTypePageList depositTypeList = depositTypeService.listDepositType(pageable);
		return new ResponseEntity<>(depositTypeList, HttpStatus.OK);
	}
	
	   
	
	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<DepositTypeDto> deleteDepositTypeById(@PathVariable("id") Long id) {
		log.debug("REST request to delete DepositType : {}", id);
		return new ResponseEntity<>(depositTypeService.deleteDepositTypeById(id), HttpStatus.OK);
	}
	
	@PutMapping(path = { "/{id}" })
	public ResponseEntity<DepositTypeDto> updateDepositType(@PathVariable("id") Long id, @Validated @RequestBody DepositTypeDto depositTypeDto) {
		log.debug("REST request to update DepositType: {}", depositTypeDto);
		return new ResponseEntity<>(depositTypeService.updateDepositType(id, depositTypeDto), HttpStatus.OK);
	}
	
	@GetMapping({ "/{id}" })
	public ResponseEntity<DepositTypeDto> getDepositType(@PathVariable("id") Long id) {
		log.debug("REST request to get DepositType : {}", id);
		return new ResponseEntity<>(depositTypeService.getDepositTypeById(id), HttpStatus.OK);
	}

	@GetMapping(path = { "/deposittypelist" })
	public ResponseEntity<List<DepositTypeDto>> listDepositType() {
		return new ResponseEntity<>(depositTypeService.listDepositType(), HttpStatus.OK);
	}


}
