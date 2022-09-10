package com.techvg.vks.society.controller;

import com.techvg.vks.society.model.ProductTypeDto;
import com.techvg.vks.society.model.ProductTypePageList;
import com.techvg.vks.society.service.ProductTypeService;
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
@RequestMapping("/api/producttype")
public class ProductTypeController {

private final ProductTypeService  productTypeService;
	
	@PostMapping
	public ResponseEntity<ProductTypeDto> addProductType(@Validated  @RequestBody ProductTypeDto productTypeDto,  Authentication authentication) {
		log.debug("REST request to save Product Type : {}", productTypeDto);
		return new ResponseEntity<>(productTypeService.addProductType(productTypeDto,authentication) , HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<ProductTypePageList> listAllProducts(Pageable pageable) {

		log.debug("REST request to get a all records of Product Type");
		ProductTypePageList productTypePageList = productTypeService.listProductType(pageable);
		return new ResponseEntity<>(productTypePageList, HttpStatus.OK);
	}
	
	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<ProductTypeDto> deleteProductTypeById(@PathVariable("id") Long id) {
		log.debug("REST request to delete Product Type : {}", id);
		return new ResponseEntity<>(productTypeService.deleteProductTypeById(id), HttpStatus.OK);
	}
	
	@PutMapping(path = { "/{id}" })
	public ResponseEntity<ProductTypeDto> updateProductType(@PathVariable("id") Long id, @Validated @RequestBody ProductTypeDto productTypeDto) {
		log.debug("REST request to update ProductType: {}", productTypeDto);
		return new ResponseEntity<>(productTypeService.updateProductType(id, productTypeDto), HttpStatus.OK);
	}
	
	@GetMapping({ "/{id}" })
	public ResponseEntity<ProductTypeDto> getProductTypeById(@PathVariable("id") Long id) {
		log.debug("REST request to get ProductType : {}", id);
		return new ResponseEntity<>(productTypeService.getProductTypeById(id), HttpStatus.OK);
	}
	
	@GetMapping( "/list/producttype")
	public ResponseEntity<List<ProductTypeDto>> listAllProductType() {
	        return new ResponseEntity<>(productTypeService.listAllProduct(), HttpStatus.OK);
	}



}
