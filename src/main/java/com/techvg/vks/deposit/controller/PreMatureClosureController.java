package com.techvg.vks.deposit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.deposit.model.DepositDto;
import com.techvg.vks.deposit.service.PreMatureClosureService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/prematureclosure")
public class PreMatureClosureController {

	private final PreMatureClosureService preMatureClosureService;

	
	  @GetMapping({ "/{id}" }) 
	  public ResponseEntity<DepositDto> preMatureDepositClosure(@PathVariable("id") Long id) {
		  System.out.println("id "+id); 
	return new ResponseEntity<>(preMatureClosureService.preMatureDepositClose(id),  HttpStatus.OK); }
	  
	 

	/*
	 * @GetMapping({ "/{accountNo}" }) public
	 * ResponseEntity<DepositDto>getAccountDetailsById(@PathVariable("accountNo")
	 * Long accountNo) { System.out.println("id "+ accountNo); return new
	 * ResponseEntity<DepositDto>(preMatureClosureService.getAccountDetailsById(
	 * accountNo), HttpStatus.OK); }
	 */
}
