package com.techvg.vks.loan.reports.LoanDisbursementRegister;

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
@RequestMapping("/api/disbursementRegister")
public class LoanDisbursementRegisterController {
	
	private final LoanDisbursementRegisterService  loanDisbursementRegisterService;
	
	@GetMapping({ "/{loanType}/{loanAmtCriteria}" })
	public byte[] getLoanDetails(@PathVariable("loanType") String loanType,@PathVariable("loanAmtCriteria") String loanAmtCriteria) {
		return loanDisbursementRegisterService.getLoanDisursementRegisterReport(loanType,loanAmtCriteria);
	}
	
	

}
