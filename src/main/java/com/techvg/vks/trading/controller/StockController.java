package com.techvg.vks.trading.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.trading.domain.Stock;
import com.techvg.vks.trading.model.SalesRegisterDto;
import com.techvg.vks.trading.model.StockDto;
import com.techvg.vks.trading.model.StockRegisterDto;
import com.techvg.vks.trading.service.StockRegisterService;
import com.techvg.vks.trading.service.StockService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/updateopeningclosing")
public class StockController {

	private final StockService stockService;
	
	@PutMapping(path = { "/{productId}" })
	public Stock updateOpeningClosing(@PathVariable("productId") Long productId) {
		log.debug("REST request to update Stock : {}");
		return stockService.updateOpeningClosing(productId);
	}

}
