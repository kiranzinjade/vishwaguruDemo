package com.techvg.vks.loan.reports.LoanDisbursementRegister;

import org.springframework.stereotype.Service;

@Service
public interface LoanDisbursementRegisterService {
	
	public byte[] getLoanDisbursementReport(String loanType, String loanAmtCriteria);

	byte[] getLoanDisursementRegisterReport(String loanType, String loanAmtCriteria);

}
