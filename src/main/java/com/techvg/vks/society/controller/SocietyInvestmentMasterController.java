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

import com.techvg.vks.society.domain.SocietyBank;
import com.techvg.vks.society.model.SocietyBankDto;
import com.techvg.vks.society.model.SocietyInvestmentDto;
import com.techvg.vks.society.model.SocietyInvestmentMasterDto;
import com.techvg.vks.society.model.SocietyInvestmentMasterPageList;
import com.techvg.vks.society.service.SocietyInvestmentMasterService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/societyinvestmentmaster")
public class SocietyInvestmentMasterController {

	private final SocietyInvestmentMasterService societyInvestmentMasterService;

	@PostMapping
	public ResponseEntity<SocietyInvestmentMasterDto> addSocietyInvestment(
			@Validated @RequestBody SocietyInvestmentMasterDto societyInvestmentMasterDto,
			Authentication authentication) {
		System.out.println("cccccccccccccccccccccc"+societyInvestmentMasterDto);
		log.debug("REST request to save SocietyInvestmentMaster : {}", societyInvestmentMasterDto);
		return new ResponseEntity<>(
				societyInvestmentMasterService.addSocietyInvestment(societyInvestmentMasterDto, authentication),
				HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<SocietyInvestmentMasterPageList> listAllSocietyInvestment(Pageable pageable) {
		log.debug("REST request to get a all records of SocietyInvestment ");
		SocietyInvestmentMasterPageList societyInvestmentMasterPageList = societyInvestmentMasterService
				.listSocietyInvestment(pageable);
		return new ResponseEntity<>(societyInvestmentMasterPageList, HttpStatus.OK);
	}

	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<SocietyInvestmentMasterDto> deleteSocietyInvestmentById(@PathVariable("id") Long id) {
		log.debug("REST request to delete SocietyInvestmentMaster : {}", id);
		System.out.println("society investment id-"+id);
		return new ResponseEntity<>(
				societyInvestmentMasterService.deleteSocietyInvestmentById(id), HttpStatus.OK);
	}

	@PutMapping(path = { "/{id}" })
	public ResponseEntity<SocietyInvestmentMasterDto> updateSocietyInvestment(@PathVariable("id") Long id,
			@Validated @RequestBody SocietyInvestmentMasterDto societyInvestmentMasterDto) {
		log.debug("REST request to update SocietyInvestmentMaster: {}", societyInvestmentMasterDto);
		System.out.println("dto - "+societyInvestmentMasterDto);
		return new ResponseEntity<>(
				societyInvestmentMasterService.updateSocietyInvestment(id, societyInvestmentMasterDto), HttpStatus.OK);
	}

	@GetMapping({ "/{id}" })
	public ResponseEntity<SocietyInvestmentMasterDto> getSocietyInvestmentById(@PathVariable("id") Long id) {
		log.debug("REST request to get Society Investment Master : {}", id);
		return new ResponseEntity<>(
				societyInvestmentMasterService.getSocietyInvestmentById(id), HttpStatus.OK);
	}

	@GetMapping("/bankList" )
	public ResponseEntity<List<SocietyBankDto>>getBankDetailsBysocietyInvestmentId() {
		log.debug("REST request to get Society Investment Master : {}");
		return new ResponseEntity<>(societyInvestmentMasterService.getBankDetailsBysocietyInvestmentId(),HttpStatus.OK);
	}
	
	@GetMapping("/schemeList/{societyBankId}" )
	public ResponseEntity<List<SocietyInvestmentMasterDto>>getSchemeBySocietyBankId(@PathVariable("societyBankId") Long societyBankId) {
		log.debug("REST request to get Society Investment Master : {}",societyBankId);
		return new ResponseEntity<>(societyInvestmentMasterService.getSchemeBySocietyBankId(societyBankId),HttpStatus.OK);
	}
}
