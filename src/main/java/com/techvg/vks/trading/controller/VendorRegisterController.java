package com.techvg.vks.trading.controller;

import com.techvg.vks.trading.model.VendorRegisterDto;
import com.techvg.vks.trading.model.VendorRegisterPageList;
import com.techvg.vks.trading.service.VendorRegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/trading/vendorregister")
public class VendorRegisterController {
	
	private final VendorRegisterService vendorRegisterService;
	
	@PostMapping
	public ResponseEntity<VendorRegisterDto> addVendorDetails(@Validated @RequestBody VendorRegisterDto vendorRegisterDto, Authentication authentication) {
		return new ResponseEntity<>(vendorRegisterService.addVendorDetails(vendorRegisterDto,authentication) , HttpStatus.CREATED);
	}

	@DeleteMapping(path = { "/{vendorId}" }) // deleting data
	public ResponseEntity<VendorRegisterDto> deleteProductById(@PathVariable("vendorId") Long vendorId) {
		return new ResponseEntity<>(vendorRegisterService.deleteVendorById(vendorId), HttpStatus.OK);
	}
	@PutMapping(path = { "/{vendorId}" })
	public ResponseEntity<VendorRegisterDto> updateProduct(@PathVariable("vendorId") Long vendorId, @Validated @RequestBody VendorRegisterDto vendorRegisterDto) {
		return new ResponseEntity<>(vendorRegisterService.updateVendorDetails(vendorId, vendorRegisterDto), HttpStatus.OK);
	}

	@GetMapping({ "/{vendorId}" })
	public ResponseEntity<VendorRegisterDto> getProduct(@PathVariable("productId") Long vendorId) {
		return new ResponseEntity<>(vendorRegisterService.getVendorById(vendorId), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<VendorRegisterPageList> listVendors(Pageable pageable) {
		return new ResponseEntity<>(vendorRegisterService.listVendors(pageable) , HttpStatus.OK);
	}
	
	 @GetMapping( "/list/vendor")
		public ResponseEntity<List<VendorRegisterDto>> listAllVendor() {
		        return new ResponseEntity<>(vendorRegisterService.listAllVendor(), HttpStatus.OK);
		}
	    

}
