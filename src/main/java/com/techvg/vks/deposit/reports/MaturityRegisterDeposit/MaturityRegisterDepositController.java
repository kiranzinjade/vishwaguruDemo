package com.techvg.vks.deposit.reports.MaturityRegisterDeposit;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.deposit.domain.DepositLedger;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor

@CrossOrigin(origins = "*")
@RequestMapping("/api/maturityregisterdeposit")
public class MaturityRegisterDepositController {

	private final MaturityRegisterDepositService maturityRegisterDepositService;

	@GetMapping({ "/{fromDate}/{toDate}" })
	public ResponseEntity<?>getMaturityReport(@PathVariable("fromDate") String fromDate,@PathVariable("toDate") String  toDate)
	{ 
			return maturityRegisterDepositService.getMaturityReport(fromDate,toDate);	
	}
	
}
