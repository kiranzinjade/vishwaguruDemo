package com.techvg.vks.loan.controller;

import java.util.List;

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

import com.techvg.vks.loan.model.LoanWatapDto;
import com.techvg.vks.loan.model.LoanWatapPageList;
import com.techvg.vks.loan.service.LoanWatapService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins="*")
@RequestMapping("api/loanwatap")
public class LoanWatapController {

private final LoanWatapService loanWatapService;
	
	@PostMapping
	public ResponseEntity<Boolean> addLoanWatap(@Validated @RequestBody List<Long> loanWatapId) {
		log.debug("REST request to save loan Watap details : {}",loanWatapId);
		return new ResponseEntity<>(loanWatapService.addLoanWatap(loanWatapId), HttpStatus.CREATED);
	}
	

	@PutMapping(path = { "/{loanWatapId}" })
	public ResponseEntity<LoanWatapDto> updateLoanWatap(@PathVariable("loanWatapId") Long loanWatapId, @RequestBody LoanWatapDto loanWatapDto) {
		log.debug("REST request to save loan Watap details : {}", loanWatapDto);
		return new ResponseEntity<>(loanWatapService.updateLoanWatap(loanWatapId,loanWatapDto), HttpStatus.OK);
	}
	
	@PutMapping(path= {"approveLoanWatap/{slot}"})
	public ResponseEntity<Boolean> approveLoanWatap(@PathVariable("slot") int slot ){
	 log.debug("REST request to Update Loan Watap Chart approve"+slot);	
	 return new ResponseEntity<>(loanWatapService.approveLoanWatap(slot), HttpStatus.OK);
	}
	
	@GetMapping({ "/{id}" })
	public ResponseEntity<LoanWatapDto> getLoanWatap(@PathVariable("id") Long id) {
		log.debug("REST request to get Loan Watap Details : {}", id);
		return new ResponseEntity<>(loanWatapService.getLoanWatapId(id), HttpStatus.OK);
	}
	
	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<LoanWatapDto> deleteLoanWatapById(@PathVariable("id") Long id) {
		log.debug("REST request to delete Loan Watap Details : {}", id);
		return new ResponseEntity<>(loanWatapService.deleteLoanWatapById(id), HttpStatus.OK);
	}
	
	@GetMapping({"/loanWataplist"})
	public ResponseEntity<LoanWatapPageList> listAllLoanWatap(Pageable pageable) {
		log.debug("REST request to get a all records of loan watap");
		LoanWatapPageList loanWatapList = loanWatapService.listAllLoanWatap(pageable);
		return new ResponseEntity<>(loanWatapList, HttpStatus.OK);
	}
	
	@GetMapping(path={"listBySlot/{slot}"})
	public ResponseEntity<List<LoanWatapDto>> listLoanWatapBySlot(@PathVariable("slot") int slot)
	{
	   log.debug("REST request to get record By slot wise", slot);
	   return new ResponseEntity<>(loanWatapService.listLoanWatapBySlot(slot), HttpStatus.OK);
	}
	
	@GetMapping(path={"allslotList"})
	public ResponseEntity<List<LoanWatapDto>> allSlot()
	{
		 log.debug("REST request to get  All List Of slot");
		   return new ResponseEntity<>(loanWatapService.allSlot(), HttpStatus.OK);	
	}
	
	
}
