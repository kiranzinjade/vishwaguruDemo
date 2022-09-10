package com.techvg.vks.loan.service;

import org.springframework.stereotype.Service;

import com.techvg.vks.loan.model.LoanDetailsDto;
import com.techvg.vks.loan.model.LoanClosureDto;

@Service
public interface LoanClosureService {
	
	LoanClosureDto getLoanClosureDetails(Long loanId);
	
	LoanDetailsDto closeLoan(LoanClosureDto loanclosureDto);

}
