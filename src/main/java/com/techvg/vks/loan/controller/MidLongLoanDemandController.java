package com.techvg.vks.loan.controller;

import com.techvg.vks.loan.model.LongLoanDemandPageList;
import com.techvg.vks.loan.model.MidLoanDemandPageList;
import com.techvg.vks.loan.model.MidLongLoanDemandDto;
import com.techvg.vks.loan.service.MidLongLoanDemandService;
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
@RequestMapping("/api/loan/midlongloandemand")
public class MidLongLoanDemandController {
	
	private final MidLongLoanDemandService midLongLoanDemandService;
	
	@PostMapping
	public ResponseEntity<MidLongLoanDemandDto> addMidLongLoanDemandDetails(@RequestBody MidLongLoanDemandDto midLongLoanDemandDto) {
		log.debug("REST request to save loan Demand details : {}", midLongLoanDemandDto);
		return new ResponseEntity<>( midLongLoanDemandService.addMidLongLoanDemandDetails(midLongLoanDemandDto), HttpStatus.CREATED);
	}
	
	@PutMapping(path = { "/{loanDemandId}" })
	public ResponseEntity<MidLongLoanDemandDto> updateMidLongLoanDemandDetails(@PathVariable("loanDemandId") Long loanDemandId, @RequestBody MidLongLoanDemandDto midLongLoanDemandDto) {
		log.debug("REST request to save loan Demand details : {}", midLongLoanDemandDto);
		return new ResponseEntity<>( midLongLoanDemandService.updateMidLongLoanDemandDetails(loanDemandId,midLongLoanDemandDto), HttpStatus.OK);
	}
	
	@GetMapping({ "/{id}" })
	public ResponseEntity<MidLongLoanDemandDto> getMidLongLoanDemandDetails(@PathVariable("id") Long id) {
		log.debug("REST request to get Loan Demand Details : {}", id);
		return new ResponseEntity<>(midLongLoanDemandService.getMidLongLoanDemandDetailsById(id), HttpStatus.OK);
	}
	
	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<MidLongLoanDemandDto> deleteMidLongLoanDemandDetailsById(@PathVariable("id") Long id) {
		log.debug("REST request to delete Loan Demand Details : {}", id);
		return new ResponseEntity<>(midLongLoanDemandService.deleteMidLongLoanDemandDetailsById(id), HttpStatus.OK);
	}
	
	@GetMapping({"/midloanlist"})
	public ResponseEntity<MidLoanDemandPageList> listAllMidLoanDemand(Pageable pageable) {

		log.debug("REST request to get a all records of mid term loan demand");
		MidLoanDemandPageList loanDemandList = midLongLoanDemandService.listMidLoanDemand(pageable);
		return new ResponseEntity<>(loanDemandList, HttpStatus.OK);
	}
	
	@GetMapping({"/longloanlist"})
	public ResponseEntity<LongLoanDemandPageList> listAllLongLoanDemand(Pageable pageable) {

		log.debug("REST request to get a all records of mid term loan demand");
		LongLoanDemandPageList loanDemandList = midLongLoanDemandService.listLongLoanDemand(pageable);
		return new ResponseEntity<>(loanDemandList, HttpStatus.OK);
	}

	@GetMapping({"/getMLLoanlist/{type}"})
	public ResponseEntity<List<MidLongLoanDemandDto>> getAllMLLoanList(@PathVariable("type") String type) {
		log.debug("REST request to delete Loan Demand Details : {}", type);
		return new ResponseEntity<>(midLongLoanDemandService.getMLLoanList(type), HttpStatus.OK);
	}
	
	@GetMapping({"/loanlist"})
	public ResponseEntity<List<MidLongLoanDemandDto>> listLoanDemand() {
		log.debug("REST request to  Loan Demand Details : {}");
		return new ResponseEntity<>(midLongLoanDemandService.listLoanDemand(), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<MidLoanDemandPageList> listAllLoanDemand(Pageable pageable) {

		log.debug("REST request to get a all records of  loan demand");
		MidLoanDemandPageList loanDemandList = midLongLoanDemandService.listLoanDemand(pageable);
		return new ResponseEntity<>(loanDemandList, HttpStatus.OK);
	}
	
}
