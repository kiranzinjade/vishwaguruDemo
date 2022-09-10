package com.techvg.vks.society.report.InvestmentLedger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/investmentledger")
public class InvestmentLedgerController {

	@Autowired
	InvestmentLedgerService  investmentLedgerService;
	
	@GetMapping({ "/{society_investment_id}" })
	public byte[] getInvestmentDetails(@PathVariable("society_investment_id") Long society_investment_id) {
		System.out.println("REST request to get Investment  Details : {}" +society_investment_id);
		return investmentLedgerService.getInvestmentDetails(society_investment_id);
	}
}
