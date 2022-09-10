package com.techvg.vks.trading.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.trading.model.PurchaseOrderDto;
import com.techvg.vks.trading.model.PurchaseOrderPageList;
import com.techvg.vks.trading.service.PurchaseOrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/purchaseorder")
public class PurchaseOrderController {

    private final PurchaseOrderService service;

    @PostMapping
    public ResponseEntity<PurchaseOrderDto> addPurchase(@RequestBody PurchaseOrderDto purchaseOrderDto) {
        return new ResponseEntity<>(service.addPurchaseOrder(purchaseOrderDto) , HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PurchaseOrderPageList> listAllPurchase(Pageable pageable) {
        return new ResponseEntity<>(service.listPurchaseOrder(pageable), HttpStatus.OK);
    }

    @DeleteMapping(path = { "/{id}" })
    public ResponseEntity<PurchaseOrderDto> deletePurchaseById(@PathVariable("id") Long id) {
        log.debug("REST request to delete Purchase : {}", id);
        return new ResponseEntity<>(service.deletePurchaseOrderById(id), HttpStatus.OK);
    }

    @GetMapping({ "/{id}" })
    public ResponseEntity<PurchaseOrderDto> getPurchaseById(@PathVariable("id") Long id) {
        log.debug("REST request to get Purchase : {}", id);
        return new ResponseEntity<>(service.getPurchaseOrderById(id), HttpStatus.OK);
    }
    @GetMapping(path = {"/purchase/{vendorId}"})
    public ResponseEntity<PurchaseOrderPageList> getPurchaseByVendor(@PathVariable("vendorId")Long vendorId,Pageable pageable){
    	PurchaseOrderPageList purchaseOrderPageList = service.getPurchaseByVendor(vendorId,pageable);
		  return new ResponseEntity<>(purchaseOrderPageList,HttpStatus.OK);
    }
    @GetMapping(path = {"/lastRecords"})
    public ResponseEntity<List<PurchaseOrderDto>> getPurchaseByDesc(){
    	log.debug("Rest request to get Sales by Date : {}");
    	return new ResponseEntity<>(service.getPurchaseByDesc(), HttpStatus.OK);
    }
    @GetMapping( "/list/{orderNo}")
    public ResponseEntity<PurchaseOrderDto> listAllPurchaseOrder(@PathVariable("orderNo")Long orderNo) {
    	 log.debug("REST request to get Purchase : {}", orderNo);
    	 
    	    return new ResponseEntity<>(service.listAllPurchaseOrder(orderNo), HttpStatus.OK);
    }
}
