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

import com.techvg.vks.trading.model.SalesOrderDto;
import com.techvg.vks.trading.model.SalesOrderPageList;
import com.techvg.vks.trading.service.SalesOrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/salesorder")
public class SalesOrderController {

    private final SalesOrderService service;

    @PostMapping
    public ResponseEntity<SalesOrderDto> addSales(@RequestBody SalesOrderDto salesOrderDto) {
        return new ResponseEntity<>(service.addSalesOrder(salesOrderDto) , HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<SalesOrderPageList> listAllSales(Pageable pageable) {
        return new ResponseEntity<>(service.listSalesOrder(pageable), HttpStatus.OK);
    }

    @DeleteMapping(path = { "/{id}" })
    public ResponseEntity<SalesOrderDto> deleteSalesById(@PathVariable("id") Long id) {
        log.debug("REST request to delete Purchase : {}", id);
        return new ResponseEntity<>(service.deleteSalesOrderById(id), HttpStatus.OK);
    }

    @GetMapping({ "/{id}" })
    public ResponseEntity<SalesOrderDto> getSalesById(@PathVariable("id") Long id) {
        log.debug("REST request to get Purchase : {}", id);
        return new ResponseEntity<>(service.getSalesOrderById(id), HttpStatus.OK);
    }
    

    @GetMapping(path = {"/sales/{memberId}"})
    public ResponseEntity<SalesOrderPageList> getSalesByMember(@PathVariable("memberId")Long memberId,Pageable pageable){
    	SalesOrderPageList salesOrderPageList = service.getSalesByMember(memberId,pageable);
		  return new ResponseEntity<>(salesOrderPageList,HttpStatus.OK);
    }
    @GetMapping(path = {"/lastRecords"})
    public ResponseEntity<List<SalesOrderDto>> getSalesByDesc(){
    	log.debug("Rest request to get Sales by Date : {}");
    	return new ResponseEntity<>(service.getSalesByDesc(), HttpStatus.OK);
    }
    
    @GetMapping( "/list/{orderNo}")
    public ResponseEntity<SalesOrderDto> listAllSalesOrder(@PathVariable("orderNo")Long orderNo) {
    	 log.debug("REST request to get Purchase : {}", orderNo);
            return new ResponseEntity<>(service.listAllSalesOrder(orderNo), HttpStatus.OK);
    }
    
    @GetMapping( "/saleslist")
    public List listAllSales() {
    	 log.debug("REST request to get sales : {}");
            return service.listAllSales();
    }
}
