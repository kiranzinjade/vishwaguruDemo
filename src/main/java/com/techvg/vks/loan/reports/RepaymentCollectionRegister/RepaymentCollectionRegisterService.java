package com.techvg.vks.loan.reports.RepaymentCollectionRegister;

import org.springframework.stereotype.Service;

@Service
public interface RepaymentCollectionRegisterService {

	 byte[] getRepaymentCollectionReport(String loanType, String loanClassfication);
	
	 byte[] getRepaymentCollectionRegisterReport(String loanType,String loanClassfication);

}
