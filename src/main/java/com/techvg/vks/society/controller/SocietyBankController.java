package com.techvg.vks.society.controller;

import com.techvg.vks.society.model.SocietyBankDto;
import com.techvg.vks.society.model.SocietyBankPageList;
import com.techvg.vks.society.service.SocietyBankService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/bank")
public class SocietyBankController {

	private final SocietyBankService bankService;

	@PostMapping
	public ResponseEntity<SocietyBankDto> addBank( @Validated  @RequestBody SocietyBankDto bankDto) {
		log.debug("REST request to save BankMaster : {}", bankDto);
		return new ResponseEntity<>( bankService.addNewBank(bankDto), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<SocietyBankPageList> listAllBanks(Pageable pageable) {

		log.debug("REST request to get a all records of BankMasters");
		SocietyBankPageList bankList = bankService.listBanks(pageable);
		return new ResponseEntity<>(bankList, HttpStatus.OK);
	}

	@GetMapping(path = { "/societybanklist" })
	public ResponseEntity<List<SocietyBankDto>> listBanks() {
		return new ResponseEntity<>(bankService.listBank(), HttpStatus.OK);
	}

	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<SocietyBankDto> deleteBankById(@PathVariable("id") Long id) {
		log.debug("REST request to delete BankMaster : {}", id);
		return new ResponseEntity<>(bankService.deleteBankById(id), HttpStatus.OK);
	}

	@PutMapping(path = { "/{bankId}" })
	public ResponseEntity<SocietyBankDto> updateBank(@PathVariable("bankId") Long bankId, @Validated @RequestBody SocietyBankDto bankDto, Authentication authentication) {
		log.debug("REST request to update BankMaster : {}", bankDto);
		return new ResponseEntity<>(bankService.updateBank(bankId, bankDto), HttpStatus.OK);
	}

	@GetMapping({ "/{id}" })
	public ResponseEntity<SocietyBankDto> getBank(@PathVariable("id") Long id) {
		log.debug("REST request to get BankMaster : {}", id);
		return new ResponseEntity<>(bankService.getBankById(id), HttpStatus.OK);
	}

}
