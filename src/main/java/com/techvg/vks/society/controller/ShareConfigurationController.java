
package com.techvg.vks.society.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.society.model.SocietyConfigurationDto;
import com.techvg.vks.society.service.ShareConfigurationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/shareconfiguration")
public class ShareConfigurationController {


	private final ShareConfigurationService shareConfigurationService;

	@PostMapping("/addMaximumShareLimit")
	public ResponseEntity<SocietyConfigurationDto> addMaximumShareLimit(@Validated @RequestBody SocietyConfigurationDto societyConfigurationDto, Authentication authentication) {
		log.debug("REST request to save Authorised Capital : {}", societyConfigurationDto);
		return new ResponseEntity<>(shareConfigurationService.setMaximumShareLimit(societyConfigurationDto, authentication),HttpStatus.OK);
	}
	  
	@PostMapping("/sharevalue")
	public ResponseEntity<SocietyConfigurationDto> addShareValue(@Validated @RequestBody SocietyConfigurationDto societyConfigurationDto, Authentication authentication) {
		log.debug("REST request to save share value : {}", societyConfigurationDto);
		return new ResponseEntity<>(shareConfigurationService.setShareValue(societyConfigurationDto, authentication),HttpStatus.OK);
	}
	

	@PostMapping("/updateTotalNoOfShares")
	public ResponseEntity<SocietyConfigurationDto> updateTotalNumberOfShares(@Validated @RequestBody SocietyConfigurationDto societyConfigurationDto, Authentication authentication) {
		log.debug("REST request to save Total Number Of Shares : {}", societyConfigurationDto);
		return new ResponseEntity<>(shareConfigurationService.updateTotalNumberOfShares(societyConfigurationDto, authentication), HttpStatus.OK);
	}

	// Loan settlement Configuration
	
	@PostMapping("/shortterm/principleamount")
	public ResponseEntity<SocietyConfigurationDto> setShortTermPrincipleAmount(@Validated @RequestBody SocietyConfigurationDto societyConfigurationDto, Authentication authentication) {
		log.debug("REST request to save share value : {}", societyConfigurationDto);
		return new ResponseEntity<>(shareConfigurationService.updateConfigValue(societyConfigurationDto, authentication),HttpStatus.OK);
	}
	
	@PostMapping("/shortterm/interest")
	public ResponseEntity<SocietyConfigurationDto> setShortTermInterest(@Validated @RequestBody SocietyConfigurationDto societyConfigurationDto, Authentication authentication) {
		log.debug("REST request to save share value : {}", societyConfigurationDto);
		return new ResponseEntity<>(shareConfigurationService.updateConfigValue(societyConfigurationDto, authentication),HttpStatus.OK);
	}

	@PostMapping("/midterm/principleamount")
	public ResponseEntity<SocietyConfigurationDto> setMidTermPrincipleAmount(@Validated @RequestBody SocietyConfigurationDto societyConfigurationDto, Authentication authentication) {
		log.debug("REST request to save share value : {}", societyConfigurationDto);
		return new ResponseEntity<>(shareConfigurationService.updateConfigValue(societyConfigurationDto, authentication),HttpStatus.OK);
	}
	
	@PostMapping("/midterm/interest")
	public ResponseEntity<SocietyConfigurationDto> setMidTermInterest(@Validated @RequestBody SocietyConfigurationDto societyConfigurationDto, Authentication authentication) {
		log.debug("REST request to save share value : {}", societyConfigurationDto);
		return new ResponseEntity<>(shareConfigurationService.updateConfigValue(societyConfigurationDto, authentication),HttpStatus.OK);
	}

	@PostMapping("/longterm/principleamount")
	public ResponseEntity<SocietyConfigurationDto> setLongTermPrincipleAmount(@Validated @RequestBody SocietyConfigurationDto societyConfigurationDto, Authentication authentication) {
		log.debug("REST request to save share value : {}", societyConfigurationDto);
		return new ResponseEntity<>(shareConfigurationService.updateConfigValue(societyConfigurationDto, authentication),HttpStatus.OK);
	}
	
	@PostMapping("/longterm/interest")
	public ResponseEntity<SocietyConfigurationDto> setLongTermInterest(@Validated @RequestBody SocietyConfigurationDto societyConfigurationDto, Authentication authentication) {
		log.debug("REST request to save share value : {}", societyConfigurationDto);
		return new ResponseEntity<>(shareConfigurationService.updateConfigValue(societyConfigurationDto, authentication),HttpStatus.OK);
	}

	
	@GetMapping({ "/all" })
	public List<SocietyConfigurationDto> listAllByType() {
		return  shareConfigurationService.getAllByType();
	}
	
	@PutMapping("/addconfiguration")
	public ResponseEntity<List<SocietyConfigurationDto>> addConfi(@RequestBody List<SocietyConfigurationDto> societyConfigurationDtoList, Authentication authentication) {
		log.debug("REST request to save Authorised Capital : {}", societyConfigurationDtoList);
		return new ResponseEntity<>(shareConfigurationService.addConfi(societyConfigurationDtoList, authentication),HttpStatus.OK);
	}
}
