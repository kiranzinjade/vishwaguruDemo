package com.techvg.vks.loan.controller;

import com.techvg.vks.loan.model.LoanInterestDetails;
import com.techvg.vks.loan.model.ShortTermSubsidyDto;
import com.techvg.vks.loan.model.ShortTermSubsidyPageList;
import com.techvg.vks.loan.model.SubsidyDtoPageList;
import com.techvg.vks.loan.service.ShortTermSubsidyervice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/loan/subsidy")
public class ShortTermSubsidyController {
	
	private final ShortTermSubsidyervice shortTermSubsidyervice;
	
	@PostMapping(path = { "/{loanId}" })//not need only for testing
	public ResponseEntity<ShortTermSubsidyDto> addSubsidyDetails(@PathVariable("loanId") Long loanId, @RequestBody LoanInterestDetails loanInterestDetails) {
		log.debug("REST request to save subsidy details : {}", loanInterestDetails);
		return new ResponseEntity<>( shortTermSubsidyervice.addSubsidyDetails(loanId,loanInterestDetails), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<ShortTermSubsidyPageList> listAll(Pageable pageable) {

		log.debug("REST request to get a all records");
		return new ResponseEntity<>(shortTermSubsidyervice.list(pageable), HttpStatus.OK);
	}

	@GetMapping(path = { "/{loanId}" })
	public ResponseEntity<SubsidyDtoPageList> listSubsidy(Pageable pageable,@PathVariable("loanId") Long loanId) {
	log.debug("REST request to get a all records");
		return new ResponseEntity<>(shortTermSubsidyervice.listSubsidy(pageable,loanId), HttpStatus.OK);
	}
	
	@PutMapping(path = { "/{subsidyId}" })
	public ResponseEntity<ShortTermSubsidyDto> updateSubsidyDetails(@PathVariable("subsidyId") Long subsidyId, @RequestBody ShortTermSubsidyDto shortTermSubsidyDto) {
		log.debug("REST request to update subsidy details : {}", shortTermSubsidyDto);
		return new ResponseEntity<>( shortTermSubsidyervice.updateSubsidyDetails(subsidyId,shortTermSubsidyDto), HttpStatus.OK);
	}
	


}
