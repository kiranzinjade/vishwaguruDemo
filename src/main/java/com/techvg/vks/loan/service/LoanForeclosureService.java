package com.techvg.vks.loan.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.techvg.vks.loan.model.LoanDetailsDto;
import com.techvg.vks.loan.model.LoanInterestDetails;
import com.techvg.vks.loan.model.RepaymentDto;

@Service
public interface LoanForeclosureService {
	
	LoanInterestDetails getForeclosureAmtShortLoan(Long loanId,String foreclosureDate);

	LoanDetailsDto forecloseShortMidLongLoan(Long loanId, LoanInterestDetails loanInterestDetails);
	
	LoanInterestDetails getForeclosureAmtMidLongLoan (Long loanId,String foreclosureDate);
	
	

}
