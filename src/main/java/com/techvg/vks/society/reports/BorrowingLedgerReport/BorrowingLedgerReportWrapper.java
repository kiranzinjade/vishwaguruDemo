package com.techvg.vks.society.reports.BorrowingLedgerReport;

import java.util.Date;

import com.techvg.vks.society.report.InvestmentLedger.InvestmentLedgerWrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingLedgerReportWrapper {
	
	private String purpose;
	private Long loanNo;
	private Date date;
	private Double loanAmt;
	private Date dueDate;
	private Double interest;
	private String crop;
	private Date transactionDate;
	private String particulars;
	private Double debit;
	private Double credit;
	private Double balance;
	private String initials;
	private String remarks;
	private Integer noOfDays;
	private Double totalInterest;
	private Double interestPaid;
	private Double interestDue;
	private String bankName;

}
