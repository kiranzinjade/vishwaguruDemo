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
import com.techvg.vks.society.model.AssetsRegistrationDto;
import com.techvg.vks.society.model.AssetsRegistrationPageList;
import com.techvg.vks.society.service.AssetsRegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/assetsRegistration")
public class AssetsRegistrationController {

	private final AssetsRegistrationService assetsRegistrationService;

	@PostMapping
	public ResponseEntity<AssetsRegistrationDto> addPurchaseAssets(
			@Validated @RequestBody AssetsRegistrationDto assetsRegistrationDto, Authentication authentication) {
		log.debug("REST request to save Assets : {}", assetsRegistrationDto);
		return new ResponseEntity<AssetsRegistrationDto>(
				assetsRegistrationService.addPurchaseAssets(assetsRegistrationDto,authentication),
				HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<AssetsRegistrationPageList> listAllAssetsRegistration(Pageable pageable) {

		log.debug("REST request to get a all records of assets");
		AssetsRegistrationPageList assetsRegistrationPageList = assetsRegistrationService
				.listAssetsRegistration(pageable);
		return new ResponseEntity<>(assetsRegistrationPageList, HttpStatus.OK);
	}

	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<AssetsRegistrationDto> deleteAssetsRegistrationById(@PathVariable("id") long id) {
		log.debug("REST request to delete Assets Registration : {}", id);
		return new ResponseEntity<AssetsRegistrationDto>(assetsRegistrationService.deleteAssetsRegistrationById(id),
				HttpStatus.OK);
	}

	@PutMapping(path = { "/{id}/{assetsId}" })
	public ResponseEntity<AssetsRegistrationDto> updateAssetsRegistration(@PathVariable("id") Long id,
			@Validated @RequestBody AssetsRegistrationDto assetsRegistrationDto) {
		log.debug("REST request to update Assets Registration : {}", assetsRegistrationDto);
		return new ResponseEntity<AssetsRegistrationDto>(
				assetsRegistrationService.updateAssetsRegistration(id, assetsRegistrationDto), HttpStatus.OK);
	}

	@GetMapping({ "/{id}" })
	public ResponseEntity<AssetsRegistrationDto> getAssetsRegistration(@PathVariable("id") long id) {
		log.debug("REST request to get Assets Registration : {}", id);
		return new ResponseEntity<AssetsRegistrationDto>(assetsRegistrationService.getAssetsRegistrationById(id),
				HttpStatus.OK);
	}
}
