package com.techvg.vks.society.controller;

import java.util.ArrayList;
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

import com.techvg.vks.society.model.SocietyConfigHistoryDto;
import com.techvg.vks.society.model.SocietyConfigHistoryPageList;
import com.techvg.vks.society.model.SocietyConfigurationDto;
import com.techvg.vks.society.model.SocietyConfigurationPageList;
import com.techvg.vks.society.service.SocietyConfigurationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/societyconfiguration")
public class SocietyConfigurationController {
	private final SocietyConfigurationService societyConfigurationService;

	@PostMapping
	public ResponseEntity<SocietyConfigurationDto> addAuthorisedCapital(
			@Validated @RequestBody SocietyConfigurationDto societyConfigurationDto, Authentication authentication) {
		log.debug("REST request to save Authorised Capital : {}", societyConfigurationDto);
		return new ResponseEntity<>(societyConfigurationService.setAuthorisedCapital(societyConfigurationDto, authentication),
				HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<SocietyConfigHistoryPageList> listRevisonHistroyForType(Pageable pageable) {
		log.debug("REST request to get all records of Revision history:");
		SocietyConfigHistoryPageList societyConfigHistoryPageList = societyConfigurationService
				.listRevisonHitroyForType(pageable);
		return new ResponseEntity<>(societyConfigHistoryPageList, HttpStatus.OK);
	}

	@GetMapping({ "/{configType}" })
	public List<SocietyConfigHistoryDto> listRevisonHistroyByType(@PathVariable("configType") String configType) {
		log.debug("REST request to get Revision history : {}", configType);
		List<SocietyConfigHistoryDto> societyConfigHistoryDto = new ArrayList<>();
		return societyConfigHistoryDto = societyConfigurationService.getAuthorisedCapitalByType(configType);
	}

	@GetMapping({ "/all" })
	public List<SocietyConfigurationDto> listAll() {
		return  societyConfigurationService.getAll();
	}
	@PutMapping(path = {"/{id}"})
	public ResponseEntity<SocietyConfigurationDto> updateSocietyConfig(@PathVariable("id") Long id, @Validated @RequestBody SocietyConfigurationDto societyConfigurationDto) {
		log.debug("REST request to update parameter: {}", societyConfigurationDto);
		return new ResponseEntity<>(societyConfigurationService.updateSocietyConfig(id, societyConfigurationDto), HttpStatus.OK);
	}
	@GetMapping({"/configList"})
	public ResponseEntity<SocietyConfigurationPageList> listConfigForType(Pageable pageable) {
		log.debug("REST request to get all records of Revision history:");
		SocietyConfigurationPageList societyConfigurationPageList = societyConfigurationService.listConfigForType(pageable);
		return new ResponseEntity<>(societyConfigurationPageList, HttpStatus.OK);
	}
	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<SocietyConfigurationDto> deleteConfigById(@PathVariable("id") Long id) {
		log.debug("REST request to delete parameter : {}", id);
		return new ResponseEntity<>(societyConfigurationService.deleteConfigById(id), HttpStatus.OK);
	}
	@GetMapping({ "/societyConfig/{configType}" })
	public ResponseEntity<SocietyConfigurationPageList> listByConfigType(@PathVariable("configType") String configType,Pageable pageable) {
		SocietyConfigurationPageList societyConfigurationPageList = societyConfigurationService.listByConfigType(configType,pageable);
		return new ResponseEntity<>(societyConfigurationPageList,HttpStatus.OK);
	}
	
}
