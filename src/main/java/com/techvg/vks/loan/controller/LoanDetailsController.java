package com.techvg.vks.loan.controller;

import com.techvg.vks.loan.model.*;
import com.techvg.vks.loan.service.LoanDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/loan")
public class LoanDetailsController {

	private final LoanDetailsService loanDetailsService;

	@PostMapping(path={"/registerstloan"})
	public ResponseEntity<LoanDetailsDto> registerSTLoan(@RequestBody LoanDetailsDto loanDetailsDto){
		return new ResponseEntity<>(loanDetailsService.registerSTLoan(loanDetailsDto), HttpStatus.CREATED);
	}

	@PutMapping(path = { "bulkresigtration/{slot}" })
	public ResponseEntity<List<LoanDetailsDto>> registerSTLoans(@PathVariable("slot") Integer slot){
		return new ResponseEntity<>(loanDetailsService.registerAllSTLoansByKmp(slot), HttpStatus.CREATED);
	}

	@PostMapping(path = { "/disbursement" })
	public ResponseEntity<DisbursementDto> addDisbursementDetails(@RequestBody DisbursementDto disbursementDto) {
		log.debug("REST request to save disbursement details : {}", disbursementDto);
		return new ResponseEntity<>(loanDetailsService.addDisbursementDetails(disbursementDto),
				HttpStatus.CREATED);
	}

	@PutMapping(path = { "/{loanId}" })
	public ResponseEntity<LoanDetailsDto> updateLoanDetails(@PathVariable("loanId") Long loanId,
			@RequestBody LoanDetailsDto loanDetailsDto) {
		log.debug("REST request to update loan details : {}", loanDetailsDto);
		return new ResponseEntity<>(loanDetailsService.updateLoanDetails(loanId, loanDetailsDto),
				HttpStatus.OK);
	}

	@PutMapping(path = { "/loanclose/{loanId}" })
	public ResponseEntity<LoanDetailsDto> updateLoanDetailsforcloser(@PathVariable("loanId") Long loanId) {
		//log.debug("REST request to update loan details : {}", loanDetailsDto);
		return new ResponseEntity<>(loanDetailsService.updateLoanDetailsforcloser(loanId),
				HttpStatus.OK);
	}
	
	
	@PutMapping(path = { "/update/{disbursementId}" })
	public ResponseEntity<DisbursementDto> updateDisbursementDetails(
			@PathVariable("disbursementId") Long disbursementId, @RequestBody DisbursementDto disbursementDto) {
		log.debug("REST request to update disbursement details : {}", disbursementDto);
		return new ResponseEntity<>(
				loanDetailsService.updateDisbursementDetails(disbursementId, disbursementDto), HttpStatus.OK);
	}

	@GetMapping({ "/{loanId}" })
	public ResponseEntity<LoanDetailsDto> getLoanDetails(@PathVariable("loanId") Long loanId) {
		return new ResponseEntity<>(loanDetailsService.getLoanDetailsById(loanId), HttpStatus.OK);
	}

	@GetMapping( "/list/{memberId}")
	public ResponseEntity<List<LoanDetailsDto>> listLoanDetailsByMemberId(@PathVariable("memberId") Long memberId) {
	      return new ResponseEntity<>(loanDetailsService.listAccountNoByMemberId(memberId), HttpStatus.OK);
	}

	@GetMapping( "/list/accNo")
	public ResponseEntity<List<LoanDetailsDto>> listAllAccountNo() {
	        return new ResponseEntity<>(loanDetailsService.listAllAccountNo(), HttpStatus.OK);
	}

	@GetMapping({ "/get/{disbursementId}" })
	public ResponseEntity<DisbursementDto> getDisbursementDetails(
			@PathVariable("disbursementId") Long disbursementId) {
		return new ResponseEntity<>(loanDetailsService.getDisbursementById(disbursementId),
				HttpStatus.OK);
	}

	// Listing Page

	@GetMapping
	public ResponseEntity<ListingPage_PageList> getLoanMemberDetails(Pageable pageable) {

		ListingPage_PageList loanList = loanDetailsService.listLoanMemberDetails(pageable);

		return new ResponseEntity<>(loanList, HttpStatus.OK);
	}

	// View Loan Details
	@GetMapping({ "/viewloandetails" })
	public ResponseEntity<ViewLoanDetailsPageList> getLoanMembers(Pageable pageable) {

		log.debug("REST request to get a all records of LoanMember");
		ViewLoanDetailsPageList loanList = loanDetailsService.listLoanMembers(pageable);

		return new ResponseEntity<>(loanList, HttpStatus.OK);
	}
	@GetMapping({ "/npalist" })
	public ResponseEntity<List<LoanInterestDto>>getAllNpa(Pageable pageable) {

		log.debug("REST request to get a all records of Npa");
		List<LoanInterestDto> npaList = loanDetailsService.listNpa(pageable);

		return new ResponseEntity<>(npaList, HttpStatus.OK);
	}
/*	@PostMapping(path = { "/amortization/{loanId}" })
	public ResponseEntity<AmortizationDto> scheduleAmortization(@PathVariable("loanId") Long loanId) {

		log.debug("REST request to save Amortization details : {}", loanId);

		return new ResponseEntity<>( loanDetailsService.scheduleAmortization(loanId), HttpStatus.CREATED);
	}*/

	@GetMapping({ "/InterestDetails/{loanId}" })
	public ResponseEntity<LoanInterestDetails> getLoanInterestDetails(@PathVariable("loanId") Long loanId) {
		log.debug("REST request to get Loan deatils : {}", loanId);

		return new ResponseEntity<>(loanDetailsService.calculateLoanInterest(loanId,new Date()), HttpStatus.OK);
	}

	@GetMapping( "/disbursementlist/{loanId}")
	public ResponseEntity<List<DisbursementDto>> getDisbursementListByLoanId(@PathVariable("loanId") Long loanId) {
	        return new ResponseEntity<>(loanDetailsService.getDisbursementListByLoanId(loanId), HttpStatus.OK);
	}

	@GetMapping( "/stLoanList/{memberId}")
	public ResponseEntity<List<LoanBriefDto>> getSTLoanList(@PathVariable("memberId") Long memberId) {
		return new ResponseEntity<>(loanDetailsService.getSTLoanList(memberId), HttpStatus.OK);
	}

	@GetMapping( "/mtLoanList/{memberId}")
	public ResponseEntity<List<LoanBriefDto>> getMTLoanList(@PathVariable("memberId") Long memberId) {
		return new ResponseEntity<>(loanDetailsService.getMTLoanList(memberId), HttpStatus.OK);
	}

	@GetMapping( "/ltLoanList/{memberId}")
	public ResponseEntity<List<LoanBriefDto>> getLTLoanList(@PathVariable("memberId") Long memberId) {
		return new ResponseEntity<>(loanDetailsService.getLTLoanList(memberId), HttpStatus.OK);
	}

	@GetMapping( "/activeLoanList")
	public ResponseEntity<List<LoanBriefDto>> getLoanList() {
		return new ResponseEntity<>(loanDetailsService.getActiveLoanList(), HttpStatus.OK);
	}
	
	@GetMapping( "/loandetails/{memberId}")
	public ResponseEntity<List<LoanDetailsDto>> getLoanDetailsByMemberId(@PathVariable("memberId") Long memberId) {
	      return new ResponseEntity<>(loanDetailsService.getLoanDetailsByMemberId(memberId), HttpStatus.OK);
	}
	
	@GetMapping({ "/croploandetails" })
	public ResponseEntity<CropLoanDetailsPageList> getCropLoanMembers(Pageable pageable) {

		log.debug("REST request to get a all records of LoanMember");
		CropLoanDetailsPageList loanList = loanDetailsService.listCropLoanMembers(pageable);

		return new ResponseEntity<>(loanList, HttpStatus.OK);
	}
	
	@GetMapping( "/amortization/list/{loanId}")
	public ResponseEntity<List<AmortizationDto>> getAmortizationList(@PathVariable("loanId") Long loanId) {
	      return new ResponseEntity<>(loanDetailsService.getAmortizationList(loanId), HttpStatus.OK);
	}
	
	////////Dashboard
	@GetMapping( "/standardCount")
	public double getStandardLoanCount() {
	      return loanDetailsService.getStandardLoanCount();
	}
	
	@GetMapping( "/defaulterCount")
	public double getDefaultLoanCount() {
	      return loanDetailsService.getDefaultLoanCount();
	}
	
	@GetMapping( "/defaultLoanList")
	public ResponseEntity<List<LoanBriefDto>> getDefaultLoanList() {
		return new ResponseEntity<>(loanDetailsService.getDefaultLoanList(), HttpStatus.OK);
	}
	
	/////loan member list
	@GetMapping( "/shortloanlist")
	public ResponseEntity<List<LoanDetailsDto>> getShortLoanList() {
		return new ResponseEntity<>(loanDetailsService.getShortLoanList(), HttpStatus.OK);
	}
	
	@GetMapping( "/midlongloanlist")
	public ResponseEntity<List<LoanDetailsDto>> getMidLongLoanList() {
		return new ResponseEntity<>(loanDetailsService.getMidLongLoanList(), HttpStatus.OK);
	}
}
