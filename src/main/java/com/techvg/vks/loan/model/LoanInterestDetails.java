package com.techvg.vks.loan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanInterestDetails {

	Long id;

	Long loanId;

	Double currOutstandingPrincipal;
	Double totalOutstandingPrincipal;

	Double currentLoanInterest;
	Double totalLoanInterest;

	Double totalCurrentOutstanding;
	Double totalOutstanding;

	Double penaltyAmount=0.0;
	Double surchargeAmt=0.0;

	// For Crop Loan
	Double selfPayInterest;
	Double stateGovPayInterest;
	Double centralGovPayInterest;
	Double dccPayInterest;

	// For Foreclosure
	Double foreclosureChargeAmt;
	Double foreclosureSurchargeAmt;

	// Including   principle,  interest and penalty
	Double totalPaymentAmt;

	//For Mid and Long term loans - Amt required to regularise the EMI's (OverDue EMI's, Current EMI and Penalty)
	Double barrierAmt;

	Date lastPaymentDate;
	
}
