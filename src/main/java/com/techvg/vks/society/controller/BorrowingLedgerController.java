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

import com.techvg.vks.society.model.BorrowingLedgerDto;
import com.techvg.vks.society.model.BorrowingLedgerPageList;
import com.techvg.vks.society.service.BorrowingLedgerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/borrowingLedger")
public class BorrowingLedgerController {
	
	private final BorrowingLedgerService  borrowingLedgerService;

	@PostMapping
	public ResponseEntity<BorrowingLedgerDto> addborrowingDetails( @RequestBody BorrowingLedgerDto borrowingLedgerDto,  Authentication authentication) {
		log.debug("REST request to save Purchase Register : {}", borrowingLedgerDto);
		return new ResponseEntity<>(borrowingLedgerService.addborrowingDetails(borrowingLedgerDto) , HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<BorrowingLedgerPageList> listAllBorrowingDetails(Pageable pageable) {

		log.debug("REST request to get a all records of Purchase");
		BorrowingLedgerPageList borrowingList = borrowingLedgerService.listborrow(pageable);
		return new ResponseEntity<>(borrowingList, HttpStatus.OK);
	}
	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<BorrowingLedgerDto> deleteBorrowById(@PathVariable("id") Long id) {
		log.debug("REST request to delete Purchase : {}", id);
		return new ResponseEntity<>(borrowingLedgerService.deleteBorrowById(id), HttpStatus.OK);
	}
	
	@PutMapping(path = { "/{id}" })
	public ResponseEntity<BorrowingLedgerDto> updateBorrowList(@PathVariable("id") Long id, @Validated @RequestBody BorrowingLedgerDto borrowingLedgerDto) {
		log.debug("REST request to update Purchase : {}", borrowingLedgerDto);
		return new ResponseEntity<>(borrowingLedgerService.updateBorrowList(id, borrowingLedgerDto), HttpStatus.OK);
	}
	
	@GetMapping({ "/{id}" })
	public ResponseEntity<BorrowingLedgerDto> getBorrowListById(@PathVariable("id") Long id) {
		log.debug("REST request to get Purchase : {}", id);
		return new ResponseEntity<>(borrowingLedgerService.getBorrowListById(id), HttpStatus.OK);
	}
	@GetMapping(path = { "/ledgerList" })
	public ResponseEntity<List<BorrowingLedgerDto>> listLedger() {
		return new ResponseEntity<>(borrowingLedgerService.listLedger(), HttpStatus.OK);
	}
}
