package com.techvg.vks.trading.controller;

import com.techvg.vks.trading.model.PurchaseRegisterDto;
import com.techvg.vks.trading.model.PurchaseRegisterPageList;
import com.techvg.vks.trading.service.PurchaseRegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/purchaseregister")
public class PurchaseRegisterController {

	private final PurchaseRegisterService  purchaseRegisterService;

	@PostMapping
	public ResponseEntity<PurchaseRegisterDto> addPurchase( @RequestBody PurchaseRegisterDto purchaseRegisterDto,  Authentication authentication) {
		log.debug("REST request to save Purchase Register : {}", purchaseRegisterDto);
		return new ResponseEntity<>(purchaseRegisterService.addPurchase(purchaseRegisterDto,authentication) , HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<PurchaseRegisterPageList> listAllPurchase(Pageable pageable) {

		log.debug("REST request to get a all records of Purchase");
		PurchaseRegisterPageList purchaseList = purchaseRegisterService.listPurchase(pageable);
		return new ResponseEntity<>(purchaseList, HttpStatus.OK);
	}
	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<PurchaseRegisterDto> deletePurchaseById(@PathVariable("id") Long id) {
		log.debug("REST request to delete Purchase : {}", id);
		return new ResponseEntity<>(purchaseRegisterService.deletePurchaseById(id), HttpStatus.OK);
	}
	
	@GetMapping({ "/{id}" })
	public ResponseEntity<PurchaseRegisterDto> getPurchaseById(@PathVariable("id") Long id) {
		log.debug("REST request to get Purchase : {}", id);
		return new ResponseEntity<>(purchaseRegisterService.getPurchaseById(id), HttpStatus.OK);
	}
}
