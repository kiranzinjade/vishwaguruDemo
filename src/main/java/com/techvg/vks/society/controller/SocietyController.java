package com.techvg.vks.society.controller;

import com.google.gson.Gson;
import com.techvg.vks.accounts.model.VoucherTypeDto;
import com.techvg.vks.society.model.SocietyDto;
import com.techvg.vks.society.service.SocietyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techvg.vks.society.model.SocietyDto;
import com.techvg.vks.society.service.SocietyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/society") 
public class SocietyController {

	private final SocietyService societyService;

	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> addSociety(@RequestPart("society") SocietyDto society ,@RequestPart("imgFile") MultipartFile imgFile ) {
		log.debug("REST request to save Meeting details : {}", society);
		return new ResponseEntity<>(societyService.addSociety(society,imgFile), HttpStatus.CREATED);
	}
	

	@PutMapping({ "/{id}" })
	public ResponseEntity<SocietyDto> updateAgm(@PathVariable("id") Long id, SocietyDto societyDto) {
		log.debug("REST request to save Society details : {}", societyDto);
		return new ResponseEntity<>(societyService.updateSociety(societyDto, id), HttpStatus.OK);
	}

	@PutMapping(value="/{id}",produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> updateSociety(@PathVariable("id") Long id,@RequestPart("society") SocietyDto society ,@RequestPart("imgFile") MultipartFile imgFile ) {
		log.debug("REST request to save  details : {}", society);
		return new ResponseEntity<>(societyService.updateSocietyy(id,society,imgFile), HttpStatus.CREATED);
	}


	
	 @DeleteMapping(path = { "/{id}" }) // deleting data
		public ResponseEntity<SocietyDto> deleteSocietyById(@PathVariable("id") Long id) {
			log.debug("REST request to delete society : {}",id);
			return new ResponseEntity<>(societyService.deleteSocietyById(id), HttpStatus.OK);
	 }

	 
	 @GetMapping({ "/{id}" })
		public  ResponseEntity<SocietyDto> getSociety(@PathVariable("id") Long id) {
			log.debug("REST request to get Society : {}", id);
			return new ResponseEntity<>(societyService.getSocietyById(id),HttpStatus.OK);
		}
	 
	   @GetMapping
	   public ResponseEntity<List<SocietyDto>> listSocietyDetails() {
	        return new ResponseEntity<>(societyService.listSocietyDetails(), HttpStatus.OK);
	    }
}
