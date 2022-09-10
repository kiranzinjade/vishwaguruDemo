package com.techvg.vks.society.controller;

import com.techvg.vks.society.model.LoanChargesDto;
import com.techvg.vks.society.model.LoanChargesPageList;
import com.techvg.vks.society.service.LoanChargesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/loanCharges")
public class LoanChargesController {
private final LoanChargesService loanChargesService;
	
	@PostMapping
	public ResponseEntity<LoanChargesDto> addPreClosure(@Validated  @RequestBody LoanChargesDto loanChargesDto, Authentication authentication) {
		log.debug("REST request to save PreClosure : {}", loanChargesDto);
		return new ResponseEntity<>(loanChargesService.addLoanCharges(loanChargesDto,authentication) , HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<LoanChargesPageList> listAllPreClosure(Pageable pageable) {

		log.debug("REST request to get a all records of Preclosuree");
		LoanChargesPageList preClosureList = loanChargesService.listLoanCharges(pageable);
		return new ResponseEntity<>(preClosureList, HttpStatus.OK);
	}
	
	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<LoanChargesDto> deletePreClosureById(@PathVariable("id") Long id) {
		log.debug("REST request to delete PreClosure : {}", id);
		return new ResponseEntity<>(loanChargesService.deleteLoanChargesById(id), HttpStatus.OK);
	}
	
	@PutMapping(path = { "/{id}" })
	public ResponseEntity<LoanChargesDto> updatePreClosure(@PathVariable("id") Long id, @Validated @RequestBody LoanChargesDto loanChargesDto) {
		log.debug("REST request to update PreClosure: {}", loanChargesDto);
		return new ResponseEntity<>(loanChargesService.updateLoanCharges(id, loanChargesDto), HttpStatus.OK);
	}
	
	@GetMapping({ "/{id}" })
	public ResponseEntity<LoanChargesDto> getPreClosure(@PathVariable("id") Long id) {
		log.debug("REST request to get Prerequisite : {}", id);
		return new ResponseEntity<>(loanChargesService.getLoanChargesById(id), HttpStatus.OK);
	}


}
