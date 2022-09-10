package com.techvg.vks.loan.reports.ShortTermLoanLedger;

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
@RequestMapping("/api/shortTermLoan")
public class ShortTermLoanLedgerController {

	@Autowired
	ShortTermLoanLedgerService shortTermLoanLedgerService;


	@GetMapping({ "/{loanId}" })
	public byte[] getShortTermLoanReport(@PathVariable("loanId") Long loanId) {
		return shortTermLoanLedgerService.getShortTermLoanListReport(loanId);
	}

}
