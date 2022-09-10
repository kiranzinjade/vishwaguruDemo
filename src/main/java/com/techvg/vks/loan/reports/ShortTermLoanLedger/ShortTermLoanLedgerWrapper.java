package com.techvg.vks.loan.reports.ShortTermLoanLedger;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortTermLoanLedgerWrapper {
	
	private Long memberId;
	private String name;
	private Long loanId;
	private Double loanAmt;
	private Date disbursementDate;
	private Double interestRate;
	private String productName;
	private Date loanPlannedClosureDate;
	private String cropName;
	

}
