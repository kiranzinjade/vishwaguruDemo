package com.techvg.vks.society.controller;

import com.techvg.vks.society.model.NpaSettingDto;
import com.techvg.vks.society.model.NpaSettingPageList;
import com.techvg.vks.society.service.NpaSettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/npasetting") 

public class NpaSettingController {
	
	private final NpaSettingService npaSettingService;
	
	@PostMapping
	public ResponseEntity<NpaSettingDto> addNpaSetting(@Validated  @RequestBody NpaSettingDto npaSettingDto,  Authentication authentication) {
		log.debug("REST request to save Npa Setting : {}", npaSettingDto);
		return new ResponseEntity<>(npaSettingService.addNpaSetting(npaSettingDto,authentication) , HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<NpaSettingPageList> listAllNpaSettings(Pageable pageable) {

		log.debug("REST request to get a all records of Npa Setting");
		NpaSettingPageList npaSettingList = npaSettingService.listNpaSettings(pageable);
		return new ResponseEntity<>(npaSettingList, HttpStatus.OK);
	}
	
	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<NpaSettingDto> deleteNpaSettingById(@PathVariable("id") Long id) {
		log.debug("REST request to delete Npa Setting : {}", id);
		return new ResponseEntity<>(npaSettingService.deleteNpaSettingById(id), HttpStatus.OK);
	}
	@PutMapping(path = { "/{id}" })
	public ResponseEntity<NpaSettingDto> updateNpaSetting(@PathVariable("id") Long id, @Validated @RequestBody NpaSettingDto npaSettingDto) {
		log.debug("REST request to update Npa Setting : {}", npaSettingDto);
		return new ResponseEntity<>(npaSettingService.updateNpaSetting(id, npaSettingDto), HttpStatus.OK);
	}
	
	@GetMapping({ "/{id}" })
	public ResponseEntity<NpaSettingDto> getNpaSetting(@PathVariable("id") Long id) {
		log.debug("REST request to get Npa Setting : {}", id);
		return new ResponseEntity<>(npaSettingService.getNpaSettingById(id), HttpStatus.OK);
	}
	
	@GetMapping( "/list")
	public ResponseEntity<List<NpaSettingDto>> listAllNpa() {
	        return new ResponseEntity<>(npaSettingService.listAllNpa(), HttpStatus.OK);
	}
}
