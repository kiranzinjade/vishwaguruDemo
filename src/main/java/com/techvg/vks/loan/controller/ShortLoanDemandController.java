package com.techvg.vks.loan.controller;

import com.techvg.vks.loan.model.LoanDemandCountDto;
import com.techvg.vks.loan.model.LoanDemandDto;
import com.techvg.vks.loan.model.LoanDemandPageList;
import com.techvg.vks.loan.service.ShortLoanDemandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/loan/stloandemand")
public class ShortLoanDemandController {
	
	private final  ShortLoanDemandService loanDemandService;
	
	@PostMapping
	public ResponseEntity<LoanDemandDto> addLoanDemandDetails(@RequestBody LoanDemandDto loanDemandDto) {
		log.debug("REST request to save loan Demand details : {}", loanDemandDto);
		return new ResponseEntity<>( loanDemandService.addLoanDemandDetails(loanDemandDto), HttpStatus.CREATED);
	}
	
	@PutMapping(path = { "/{loanDemandId}" })
	public ResponseEntity<LoanDemandDto> updateLoanDemandDetails(@PathVariable("loanDemandId") Long loanDemandId, @RequestBody LoanDemandDto loanDemandDto) {
		log.debug("REST request to save loan Demand details : {}", loanDemandDto);
		return new ResponseEntity<>( loanDemandService.updateLoanDemandDetails(loanDemandId,loanDemandDto), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<LoanDemandPageList> listAllShortLoanDemand(Pageable pageable) {
		log.debug("REST request to get a all records of loan demand");
		LoanDemandPageList loanDemandList = loanDemandService.listShortLoanDemand(pageable);
		return new ResponseEntity<>(loanDemandList, HttpStatus.OK);
	}

	@GetMapping({ "/memberDemandList/{cropName}/{year}" })
	public ResponseEntity<LoanDemandPageList> listMemberSTDemand(Pageable pageable, @PathVariable("cropName") String cropName, @PathVariable("year") Integer year) {

		log.debug("REST request to get a all records of loan demand");
		LoanDemandPageList loanDemandList = loanDemandService.getSTLoanDemandList(pageable,  cropName,  year);
		return new ResponseEntity<>(loanDemandList, HttpStatus.OK);
	}

	@GetMapping({ "/allList" })
	public ResponseEntity<List<LoanDemandDto>> getLoanDemandDetails() {
		return new ResponseEntity<>(loanDemandService.getSTLoanDemand(), HttpStatus.OK);
	}

	@GetMapping({ "/demandcount/{year}" })
	public ResponseEntity<List<LoanDemandCountDto>> getLoanDemandCount(@PathVariable("year") Integer year) {
		return new ResponseEntity<>(loanDemandService.getMemberCountForCrop(year), HttpStatus.OK);
	}
	
	@GetMapping({ "/{id}" })
	public ResponseEntity<LoanDemandDto> getLoanDemandDetails(@PathVariable("id") Long id) {
		log.debug("REST request to get Loan Demand Details : {}", id);
		return new ResponseEntity<>(loanDemandService.getLoanDemandDetailsById(id), HttpStatus.OK);
	}
	
	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<LoanDemandDto> deleteLoanDemandDetailsById(@PathVariable("id") Long id) {
		log.debug("REST request to delete Loan Demand Details : {}", id);
		return new ResponseEntity<>(loanDemandService.deleteLoanDemandDetailsById(id), HttpStatus.OK);
	}

}
