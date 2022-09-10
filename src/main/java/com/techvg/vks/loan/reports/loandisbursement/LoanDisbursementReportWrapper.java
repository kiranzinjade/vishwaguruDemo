package com.techvg.vks.loan.reports.loandisbursement;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDisbursementReportWrapper {

	private Long memberId;
	private Long loanAccountNo;
	private String borrowerName;
	private String address;
	private String village;
	private Double sanctionedLoanAmount;
	private Double loanAmount;
	private String landArea;
	private Long resolutionNo;
	private Date resolutionDate;
	private Double costOfInvestment;
	private Double benefitingArea;
	private Long dccbLoanNo;
	private Boolean assigneeOfLand;
	private Long mortgageDeedNo;
	private Date mortgageDate;
	private Double extentMorgage;
	private String landAddress;
	private Double valueOfProperty;
	private String purpose;
	private Double wet;
	private Double dry;
	private Date installmentDate;
	private Double installmentAmt;
	private Double closingPrinciple;
	private Double interestPaid;
	private Double balanceInterest;
	private Date disbursementDate;
	private double disbursementAmt;
}
