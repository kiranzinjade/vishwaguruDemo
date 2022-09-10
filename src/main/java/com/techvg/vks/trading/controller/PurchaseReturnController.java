package com.techvg.vks.trading.controller;

import com.techvg.vks.trading.model.PurchaseOrderDto;
import com.techvg.vks.trading.model.PurchaseReturnDto;
import com.techvg.vks.trading.model.PurchaseReturnPageList;
import com.techvg.vks.trading.service.PurchaseReturnService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/purchasereturn")
public class PurchaseReturnController {

	private final PurchaseReturnService  service;

	@PostMapping
	public ResponseEntity<PurchaseReturnDto> addPurchaseReturn(@Validated @RequestBody PurchaseReturnDto purchaseReturnDto) {
		return new ResponseEntity<>(service.addPurchaseReturn(purchaseReturnDto), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<PurchaseReturnPageList> listAllSalesReturn(Pageable pageable) {
		return new ResponseEntity<>(service.listAllPurchaseReturn(pageable), HttpStatus.OK);
	}

	@GetMapping(path = { "/byVendor/{vendorId}" })
	public ResponseEntity<PurchaseReturnPageList> listAllSalesReturnByMember(Pageable pageable, @PathVariable("vendorId") Long vendorId) {
		return new ResponseEntity<>(service.listByVendor(pageable, vendorId), HttpStatus.OK);
	}
	
	 @GetMapping(path = {"/purchase/Last5"})
	    public List<PurchaseReturnDto> getPurchaseReturnByLastRecords(){
	    	log.debug("Rest request to get Sales by Date : {}");
	    	return service.getPurchaseReturnByLastRecords();
	    }
}
