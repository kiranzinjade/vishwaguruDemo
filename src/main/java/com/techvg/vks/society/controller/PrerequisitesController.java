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

import com.techvg.vks.society.model.PrerequisitesDto;
import com.techvg.vks.society.model.PrerequisitesPageList;

import com.techvg.vks.society.service.PrerequisitesService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/prerequisites")

public class PrerequisitesController {
private final PrerequisitesService prerequisitesService;
	
	@PostMapping
	public ResponseEntity<PrerequisitesDto> addPrerequisite(@Validated  @RequestBody PrerequisitesDto prerequisitesDto,  Authentication authentication) {
		log.debug("REST request to save Prerequisite : {}", prerequisitesDto);
		return new ResponseEntity<>(prerequisitesService.addPrerequisite(prerequisitesDto,authentication) , HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<PrerequisitesPageList> listAllPrerequisites(Pageable pageable) {

		log.debug("REST request to get a all records of Prerequisites");
		PrerequisitesPageList prerequisiteList = prerequisitesService.listPrerequisites(pageable);
		return new ResponseEntity<>(prerequisiteList, HttpStatus.OK);
	}
	
	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<PrerequisitesDto> deletePrerequisiteById(@PathVariable("id") Long id) {
		log.debug("REST request to delete Prerequisite : {}", id);
		return new ResponseEntity<>(prerequisitesService.deletePrerequisiteById(id), HttpStatus.OK);
	}
	
	@PutMapping(path = { "/{id}" })
	public ResponseEntity<PrerequisitesDto> updatePrerequisite(@PathVariable("id") Long id, @Validated @RequestBody PrerequisitesDto prerequisitesDto) {
		log.debug("REST request to update Prerequisite: {}", prerequisitesDto);
		return new ResponseEntity<>(prerequisitesService.updatePrereqisite(id, prerequisitesDto), HttpStatus.OK);
	}
	
	@GetMapping({ "/{id}" })
	public ResponseEntity<PrerequisitesDto> getPrereuisite(@PathVariable("id") Long id) {
		log.debug("REST request to get Prerequisite : {}", id);
		return new ResponseEntity<>(prerequisitesService.getPrerequisiteById(id), HttpStatus.OK);
	}


}
