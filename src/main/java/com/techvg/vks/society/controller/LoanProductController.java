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

import com.techvg.vks.society.model.LoanProductDto;
import com.techvg.vks.society.model.LoanProductList;
import com.techvg.vks.society.service.LoanProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/loan/product")
@CrossOrigin(origins="*")
@RequiredArgsConstructor
public class LoanProductController {
private final LoanProductService loanProductService;
	
	@PostMapping
	public ResponseEntity<LoanProductDto> addLoanProduct(@Validated  @RequestBody LoanProductDto loanProductDto,  Authentication authentication) {
		log.debug("REST request to save Prerequisite : {}", loanProductDto);
		return new ResponseEntity<>(loanProductService.addLoanProduct(loanProductDto,authentication) , HttpStatus.CREATED);
	}
	@GetMapping
	public ResponseEntity<LoanProductList> getAllLoanProducts(Pageable pageable) {

		log.debug("REST request to get a all records of Prerequisites");
		LoanProductList loanProductList = loanProductService.getAllLoanProducts(pageable);
		return new ResponseEntity<>(loanProductList, HttpStatus.OK);
	}
	
	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<LoanProductDto> deleteAllLoanProducts(@PathVariable("id") Long id) {
		log.debug("REST request to delete Prerequisite : {}", id);
		return new ResponseEntity<>(loanProductService.deleteAllLoanProducts(id), HttpStatus.OK);
	}
	

	@PutMapping(path = { "/{id}" })
	public ResponseEntity<LoanProductDto> updateLoanProduct(@PathVariable("id") Long id, @Validated @RequestBody LoanProductDto loanProductDto) {
		log.debug("REST request to update DepositType: {}", loanProductDto);
		return new ResponseEntity<>(loanProductService.updateLoanProduct(id,loanProductDto), HttpStatus.OK);
	}
	
	@GetMapping({ "/{id}" })
	public ResponseEntity<LoanProductDto> readLoanProduct(@PathVariable("id") Long id) {
		log.debug("REST request to get Prerequisite : {}", id);
		return new ResponseEntity<>(loanProductService.readLoanProduct(id), HttpStatus.OK);
	}
	
	@GetMapping(path = { "/loanproductlist/{loanType}" })
	public ResponseEntity<List<LoanProductDto>> listLoanProduct(@PathVariable("loanType") String loanType) {
		log.debug("REST request to get loan product All List : {}");
		return new ResponseEntity<>(loanProductService.listLoanProduct(loanType), HttpStatus.OK);
	}
	
	
}
