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
import com.techvg.vks.society.model.SocietyBankTransactionDto;
import com.techvg.vks.society.model.SocietyBankTransactionPageList;
import com.techvg.vks.society.service.SocietyBankTransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/bankTransaction") 
public class SocietyBankTransactionController {
	
	private final SocietyBankTransactionService societyBankTransactionService;

	@PostMapping
	public ResponseEntity<SocietyBankTransactionDto> addSocietyBankTransaction(@Validated  @RequestBody SocietyBankTransactionDto societyBankTransactionDto, Authentication authentication) {
		log.debug("REST request to save Transaction : {}", societyBankTransactionDto);
		return new ResponseEntity<SocietyBankTransactionDto>(societyBankTransactionService.addSocietyBankTransaction(societyBankTransactionDto, authentication),
				HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<SocietyBankTransactionPageList> listAllSocietyBankTransaction(Pageable pageable) {

		log.debug("REST request to get a all records of Society Bank Transaction");
		SocietyBankTransactionPageList societyBankTransactionServiceList = societyBankTransactionService.listsocietyBankTransactionService(pageable);
		return new ResponseEntity<>(societyBankTransactionServiceList, HttpStatus.OK);
	}
	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<SocietyBankTransactionDto> deleteTransactionById(@PathVariable("id") Long id) {
		log.debug("REST request to delete Bank Transaction : {}", id);
		return new ResponseEntity<SocietyBankTransactionDto>(societyBankTransactionService.deleteTransactionById(id), HttpStatus.OK);
	}

	@PutMapping(path = { "/{id}" })
	public ResponseEntity<SocietyBankTransactionDto> updateTransaction(@PathVariable("id") Long id,
			@Validated @RequestBody SocietyBankTransactionDto societyBankTransactionDto) {
		log.debug("REST request to update Transaction : {}", societyBankTransactionDto);
		return new ResponseEntity<SocietyBankTransactionDto>(societyBankTransactionService.updateTransaction(id, societyBankTransactionDto), HttpStatus.OK);
	}

	@GetMapping({ "/{id}" })
	public ResponseEntity<SocietyBankTransactionDto> getAssets(@PathVariable("id") Long id) {
		log.debug("REST request to get Transaction : {}", id);
		return new ResponseEntity<SocietyBankTransactionDto>(societyBankTransactionService.getTransactionById(id), HttpStatus.OK);
	}
}
