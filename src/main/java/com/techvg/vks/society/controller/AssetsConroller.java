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

import com.techvg.vks.accounts.model.VoucherTypeDto;
import com.techvg.vks.society.model.AssetsDto;
import com.techvg.vks.society.model.AssetsPageList;
import com.techvg.vks.society.service.AssetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/assets")
public class AssetsConroller {

	private final AssetsService assetsService;

	@PostMapping
	public ResponseEntity<AssetsDto> addAssets(@Validated @RequestBody AssetsDto assetsDto,
			Authentication authentication) {
		log.debug("REST request to save Assets : {}", assetsDto);
		return new ResponseEntity<AssetsDto>(assetsService.addAssets(assetsDto, authentication), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<AssetsPageList> listAllAssets(Pageable pageable) {

		log.debug("REST request to get a all records of assets");
		AssetsPageList assetsList = assetsService.listAssets(pageable);
		return new ResponseEntity<>(assetsList, HttpStatus.OK);
	}
	
	   @GetMapping( "/list")
	   public ResponseEntity<List<AssetsDto>> listAssets() {
	        return new ResponseEntity<>(assetsService.listAssets(), HttpStatus.OK);
	    }

	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<AssetsDto> deleteAssetsById(@PathVariable("id") long id) {
		log.debug("REST request to delete Assets : {}", id);
		return new ResponseEntity<AssetsDto>(assetsService.deleteAssetsById(id), HttpStatus.OK);
	}

	@PutMapping(path = { "/{id}" })
	public ResponseEntity<AssetsDto> updateAssets(@PathVariable("id") Long id,
			@Validated @RequestBody AssetsDto assetsDto) {
		log.debug("REST request to update assets : {}", assetsDto);
		return new ResponseEntity<AssetsDto>(assetsService.updateAssets(id, assetsDto), HttpStatus.OK);
	}

	@GetMapping({ "/{id}" })
	public ResponseEntity<AssetsDto> getAssets(@PathVariable("id") long id) {
		log.debug("REST request to get Assets : {}", id);
		return new ResponseEntity<AssetsDto>(assetsService.getAssetsById(id), HttpStatus.OK);
	}
}
