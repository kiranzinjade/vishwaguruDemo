package com.techvg.vks.trading.controller;

import com.techvg.vks.trading.model.SalesRegisterDto;
import com.techvg.vks.trading.model.SalesRegisterPageList;
import com.techvg.vks.trading.service.SalesRegisterService;
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
@RequestMapping("/api/salesregister")
public class SalesRegisterController {
	
	private final SalesRegisterService salesRegisterService;

	@PostMapping
	public ResponseEntity<SalesRegisterDto> addSales(@RequestBody SalesRegisterDto salesRegisterDto) {
		log.debug("REST request to save Sales Register : {}", salesRegisterDto);
		return new ResponseEntity<>(salesRegisterService.addSales(salesRegisterDto) , HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<SalesRegisterPageList> listAllSales(Pageable pageable) {

		log.debug("REST request to get a all records of Sales");
		SalesRegisterPageList salesList = salesRegisterService.listSales(pageable);
		return new ResponseEntity<>(salesList, HttpStatus.OK);
	}
	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<SalesRegisterDto> deleteSalesById(@PathVariable("id") Long id) {
		log.debug("REST request to delete Sales : {}", id);
		return new ResponseEntity<>(salesRegisterService.deleteSalesById(id), HttpStatus.OK);
	}

	@GetMapping({ "/{id}" })
	public ResponseEntity<SalesRegisterDto> getSales(@PathVariable("id") Long id) {
		log.debug("REST request to get Sales : {}", id);
		return new ResponseEntity<>(salesRegisterService.getSalesById(id), HttpStatus.OK);
	}
}
