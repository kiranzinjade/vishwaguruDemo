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
import com.techvg.vks.society.model.ExpenditureTypeDto;
import com.techvg.vks.society.model.ExpenditureTypePageList;
import com.techvg.vks.society.service.ExpenditureTypeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/expendituretype")
public class ExpenditureTypeController {
	
	private final ExpenditureTypeService expenditureTypeService;

	@PostMapping
	public ResponseEntity<ExpenditureTypeDto> addExpenditureType(@Validated  @RequestBody ExpenditureTypeDto expenditureTypeDto,  Authentication authentication) {
		log.debug("REST request to save expenditure Type : {}", expenditureTypeDto);
		return new ResponseEntity<>(expenditureTypeService.addExpenditureType(expenditureTypeDto,authentication) , HttpStatus.CREATED);
	}
	   @GetMapping( "/list")
	   public ResponseEntity<List<ExpenditureTypeDto>> listExpenditure() {
	        return new ResponseEntity<>(expenditureTypeService.listExpenditure(), HttpStatus.OK);
	    }
	
	@GetMapping
	public ResponseEntity<ExpenditureTypePageList> listAllExpenditures(Pageable pageable) {

		log.debug("REST request to get a all records of Expenditure Type");
		ExpenditureTypePageList expenditureTypePageList = expenditureTypeService.listExpenditureType(pageable);
		return new ResponseEntity<>(expenditureTypePageList, HttpStatus.OK);
	}
	
	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<ExpenditureTypeDto> deleteExpenditureTypeById(@PathVariable("id") Long id) {
		log.debug("REST request to delete Expenditure Type : {}", id);
		return new ResponseEntity<>(expenditureTypeService.deleteExpenditureTypeById(id), HttpStatus.OK);
	}
	
	@PutMapping(path = { "/{id}" })
	public ResponseEntity<ExpenditureTypeDto> updateExpenditureType(@PathVariable("id") Long id, @Validated @RequestBody ExpenditureTypeDto expenditureTypeDto) {
		log.debug("REST request to update Expenditure Type: {}", expenditureTypeDto);
		return new ResponseEntity<>(expenditureTypeService.updateExpenditureType(id, expenditureTypeDto), HttpStatus.OK);
	}
	
	@GetMapping({ "/{id}" })
	public ResponseEntity<ExpenditureTypeDto> getExpenditureTypeById(@PathVariable("id") Long id) {
		log.debug("REST request to getExpenditureType : {}", id);
		return new ResponseEntity<>(expenditureTypeService.getExpenditureTypeById(id), HttpStatus.OK);
	}

	

}
