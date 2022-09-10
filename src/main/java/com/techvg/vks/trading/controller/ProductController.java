package com.techvg.vks.trading.controller;

import com.techvg.vks.trading.model.ProductDto;
import com.techvg.vks.trading.model.ProductPageList;
import com.techvg.vks.trading.model.ProductVendorDto;
import com.techvg.vks.trading.model.VendorRegisterDto;
import com.techvg.vks.trading.service.ProductService;
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
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@Validated @RequestBody ProductDto productDto, Authentication authentication) {
        return new ResponseEntity<>(productService.addProduct(productDto,authentication) , HttpStatus.CREATED);
    }

    @PostMapping(path = { "/vendor/{productId}/{vendorId}" })
    public ResponseEntity<ProductVendorDto> addProductVendor(@PathVariable("vendorId") Long vendorId, @PathVariable("productId") Long productId) {
        return new ResponseEntity<>(productService.addVendor(vendorId, productId) , HttpStatus.CREATED);
    }

    @DeleteMapping(path = { "/vendor/{productId}/{vendorId}" })
    public ResponseEntity<Boolean> removeProductVendor(@PathVariable("vendorId") Long vendorId, @PathVariable("productId") Long productId, Authentication authentication) {
        return new ResponseEntity<>(productService.removeVendor(vendorId, productId) , HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ProductPageList> listAllProducts(Pageable pageable) {
        ProductPageList productPageList = productService.listProducts(pageable);
        return new ResponseEntity<>(productPageList, HttpStatus.OK);
    }

    @DeleteMapping(path = { "/{productId}" }) // deleting data
    public ResponseEntity<ProductDto> deleteProductById(@PathVariable("productId") Long productId) {
        return new ResponseEntity<>(productService.deleteProductById(productId), HttpStatus.OK);
    }
    @PutMapping(path = { "/{productId}" })
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("productId") Long productId,  @RequestBody ProductDto productDto) {
           System.out.println("productId+++++++++++++++++++++++++++++++++++++++++++++++++++++"+productId);
           System.out.println("productId+++++++++++++++++++++++++++++++++++++++++++++++++++++"+productDto.toString());
    	return new ResponseEntity<>(productService.updateProduct(productId, productDto), HttpStatus.OK);
    }

    @GetMapping({ "/{productId}" })
    public ResponseEntity<ProductDto> getProduct(@PathVariable("productId") Long productId) {
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }
    
    @GetMapping( "/list")
    public ResponseEntity<List<ProductDto>> listAllProduct() {
            return new ResponseEntity<>(productService.listAllProduct(), HttpStatus.OK);
    }
    
    @GetMapping({"/list/{vendorId}"})
    public ResponseEntity<List<ProductDto>>productListByVendor(@PathVariable("vendorId") Long vendorId){
    	
    	return new ResponseEntity<>(productService.productListByVendor(vendorId),HttpStatus.OK);
    	
    }


}
