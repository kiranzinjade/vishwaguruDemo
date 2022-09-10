package com.techvg.vks.society.controller;

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

import com.techvg.vks.society.model.InterestShortTermLoanDto;
import com.techvg.vks.society.model.InterestShortTermLoanPageList;
import com.techvg.vks.society.service.InterestShortTermLoanService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/interestshortterm") 
public class InterestShortTermLoanController {
	
	private final InterestShortTermLoanService interestShortTermLoanService;

	@PostMapping
	public ResponseEntity<InterestShortTermLoanDto> addInterestShortTermLoan(@Validated @RequestBody InterestShortTermLoanDto interestShortTermLoanDto, Authentication authentication) {
		log.debug("REST request to save Interest short term Loan : {}",interestShortTermLoanDto); 
		return new ResponseEntity<>(interestShortTermLoanService.addInterestShortTermLoan(interestShortTermLoanDto, authentication) , HttpStatus.CREATED);
	}
	
	 @PutMapping(path = { "/{id}" })
		public ResponseEntity<InterestShortTermLoanDto> updateInterestShortTermLoan(@PathVariable("id") Long id,@Validated @RequestBody InterestShortTermLoanDto interestShortTermLoanDto, Authentication authentication) {
		 log.debug("REST request to Update Interest short term Loan : {}",interestShortTermLoanDto); 
			return new ResponseEntity<>(interestShortTermLoanService.updateInterestShortTermLoan(id,interestShortTermLoanDto, authentication) , HttpStatus.CREATED);
	}
	
	 @GetMapping
		public ResponseEntity<InterestShortTermLoanPageList> listAllinterestShortTermLoan(Pageable pageable) {
			log.debug("REST request to get a all records of Interest Short Term Loan");
			InterestShortTermLoanPageList interestShortTermLoanList = interestShortTermLoanService.listAllinterestShortTermLoan(pageable);
			return new ResponseEntity<>(interestShortTermLoanList, HttpStatus.OK);
		} 
	 
	 
	 @DeleteMapping(path = { "/{id}" }) // deleting data
		public ResponseEntity<InterestShortTermLoanDto> deleteById(@PathVariable("id") Long id) {
			log.debug("REST request to delete InterestShortTermLoan : {}", id);
			return new ResponseEntity<>(interestShortTermLoanService.deleteById(id), HttpStatus.OK);
	 }
	 
	 @GetMapping({ "/{id}" })
		public ResponseEntity<InterestShortTermLoanDto> getById(@PathVariable("id") Long id) {
			log.debug("REST request to get InterestShortTermLoang : {}", id);
			return new ResponseEntity<>(interestShortTermLoanService.getById(id), HttpStatus.OK);
		}

}
