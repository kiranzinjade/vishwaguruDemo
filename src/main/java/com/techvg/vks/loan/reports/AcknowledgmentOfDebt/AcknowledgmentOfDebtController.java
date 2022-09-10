package com.techvg.vks.loan.reports.AcknowledgmentOfDebt;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor

@CrossOrigin(origins = "*")
@RequestMapping("/api/acknowledgmentofdebt")
public class AcknowledgmentOfDebtController {

	private final AcknowledgmentOfDebtService acknowledgmentOfDebtService;

	@GetMapping({ "/{endYearDate}" })
	public ResponseEntity<?>getAcknowledgmentOfDebtReport(@PathVariable("endYearDate") String endYearDate)
	{ 
			return acknowledgmentOfDebtService.getAcknowledgmentOfDebtReport(endYearDate);	
	}
	
}
