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

import com.techvg.vks.society.model.CropRegistrationDto;
import com.techvg.vks.society.model.CropRegistrationPageList;
import com.techvg.vks.society.service.CropRegistrationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/cropregistration") 

public class CropRegistrationController {
	
	private final CropRegistrationService cropRegistrationService;
	
	@PostMapping
	public ResponseEntity<CropRegistrationDto> addCropRegistration(@Validated  @RequestBody CropRegistrationDto cropRegistrationDto,  Authentication authentication) {
		log.debug("REST request to save Crop Registration : {}", cropRegistrationDto);
		return new ResponseEntity<>(cropRegistrationService.addCropRegistration(cropRegistrationDto,authentication) , HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<CropRegistrationPageList> listAllCropRegistration(Pageable pageable) {

		log.debug("REST request to get a all records of Crop Registration");
		CropRegistrationPageList cropRegistrationList = cropRegistrationService.listCropRegistration(pageable);
		return new ResponseEntity<>(cropRegistrationList, HttpStatus.OK);
	}
	
	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<CropRegistrationDto> deleteCropRegistrationById(@PathVariable("id") Long id) {
		log.debug("REST request to delete Crop Registration : {}", id);
		return new ResponseEntity<>(cropRegistrationService.deleteCropRegistrationById(id), HttpStatus.OK);
	}
	@PutMapping(path = { "/{id}" })
	public ResponseEntity<CropRegistrationDto> updateCropRegistration(@PathVariable("id") Long id, @Validated @RequestBody CropRegistrationDto cropRegistrationDto) {
		log.debug("REST request to update Crop Registration : {}", cropRegistrationDto);
		return new ResponseEntity<>(cropRegistrationService.updateCropRegistration(id, cropRegistrationDto), HttpStatus.OK);
	}
	
	@GetMapping({ "/{id}" })
	public ResponseEntity<CropRegistrationDto> getCropRegistration(@PathVariable("id") Long id) {
		log.debug("REST request to get Crop Registration : {}", id);
		return new ResponseEntity<>(cropRegistrationService.getCropRegistrationById(id), HttpStatus.OK);
	}
   
	@GetMapping(path ={ "/list" })
	public ResponseEntity<List<CropRegistrationDto>> getCrops() {
		log.debug("REST request to get crop list  deatils : {}");
		return new ResponseEntity<>(cropRegistrationService.getCrops(), HttpStatus.OK);
	}
	@GetMapping(path ={ "/list/bySeason/{season}" })
	public ResponseEntity<List<CropRegistrationDto>> getCropsBySeason(@PathVariable("season") String season) {
		log.debug("REST request to get crop list  deatils : {}");
		return new ResponseEntity<>(cropRegistrationService.getCropsBySeason(season), HttpStatus.OK);
	}
	
}
