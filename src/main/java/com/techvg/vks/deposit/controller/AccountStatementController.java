package com.techvg.vks.deposit.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.deposit.model.DepositLedgerDto;
import com.techvg.vks.deposit.service.AccountStatementService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/accountstatement")
public class AccountStatementController {

	private final AccountStatementService accountStatementService;

	@GetMapping({ "/{id}/{fromDate}/{toDate}" })
	public List<DepositLedgerDto> generateAccountStatement(@PathVariable("id") Long id,@PathVariable("fromDate") String fromDate,@PathVariable("toDate") String  toDate) {
		log.debug("REST request to get Account No : {}", id);
	return accountStatementService.generateAccountStatement(id, fromDate, toDate);
	}

}
