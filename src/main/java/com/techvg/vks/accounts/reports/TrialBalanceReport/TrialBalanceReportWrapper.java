package com.techvg.vks.accounts.reports.TrialBalanceReport;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrialBalanceReportWrapper {
	
	 	private String name;
	 	private Double debitAmt;
	    private Double creditAmt;
	    private Double balance;
	    private Double closingBalance;
	    private Integer transMonth;
	    private Integer year;
	    private Date transDate;
	    private String accHeadCode;
}
