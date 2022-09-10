package com.techvg.vks.trading.controller;

import com.techvg.vks.trading.model.SalesReturnPageList;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.techvg.vks.trading.model.PurchaseReturnDto;
import com.techvg.vks.trading.model.SalesReturnDto;
import com.techvg.vks.trading.service.SalesReturnService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/salesreturn")

public class SalesReturnController {

	private final SalesReturnService service;

	@PostMapping
	public ResponseEntity<SalesReturnDto> updateSalesReturn(@Validated @RequestBody SalesReturnDto salesReturnDto) {
		return new ResponseEntity<>(service.addSalesReturn(salesReturnDto), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<SalesReturnPageList> listAllSalesReturn(Pageable pageable) {
		return new ResponseEntity<>(service.listAllSalesReturn(pageable), HttpStatus.OK);
	}

	@GetMapping(path = { "/byMember/{memberId}" })
	public ResponseEntity<SalesReturnPageList> listAllSalesReturnByMember(Pageable pageable, @PathVariable("memberId") Long memberId) {
		return new ResponseEntity<>(service.listByMember(pageable, memberId), HttpStatus.OK);
	}
	@GetMapping(path = {"/sales/Last5"})
	    public List<SalesReturnDto> getSalesReturnByLastRecords(){
	    	log.debug("Rest request to get Sales by Date : {}");
	    	return service.getSalesReturnByLastRecords();
	    }

}
