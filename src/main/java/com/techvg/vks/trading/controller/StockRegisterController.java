package com.techvg.vks.trading.controller;

import com.techvg.vks.deposit.model.DepositLedgerDto;
import com.techvg.vks.trading.domain.StockRegister;
import com.techvg.vks.trading.model.PurchaseRegisterDto;
import com.techvg.vks.trading.model.StockRegisterDto;
import com.techvg.vks.trading.model.StockRegisterPageList;
import com.techvg.vks.trading.service.StockRegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/stockRegister")
public class StockRegisterController {
	
	private final StockRegisterService stockRegisterService;
	@GetMapping
	public ResponseEntity<StockRegisterPageList> listAllStock(Pageable pageable) {

		log.debug("REST request to get a all records of stock");
		StockRegisterPageList stockList = stockRegisterService.listStock(pageable);
		return new ResponseEntity<>(stockList, HttpStatus.OK);
	}

	@GetMapping({ "/list/{productId}" })
	public List<StockRegisterDto> getListById(@PathVariable("productId") Long productId) {
		log.debug("REST request to get Purchase : {}", productId);
		return stockRegisterService.getListById(productId);
	}
	
	@GetMapping({"/currentStock/{productId}"})
	public ResponseEntity<StockRegisterDto> getCurrentStockById(@PathVariable("productId") Long productId){
		log.debug("REST request to get all records of stock");
		return new ResponseEntity<>(stockRegisterService.getCurrentStockById(productId), HttpStatus.OK);
	}
	
	@GetMapping({ "/{fromDate}/{toDate}/{productId}" })
	public List<StockRegisterDto> getStockList(@PathVariable("productId") Long productId,@PathVariable("fromDate") String fromDate,@PathVariable("toDate") String  toDate) {
		log.debug("REST request to get product : {}", productId);
	return stockRegisterService.getStockList(productId, fromDate, toDate);
	}
	
	
}
