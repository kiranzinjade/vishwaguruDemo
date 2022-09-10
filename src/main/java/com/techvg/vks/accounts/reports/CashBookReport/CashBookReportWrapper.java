package com.techvg.vks.accounts.reports.CashBookReport;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashBookReportWrapper {

	private Date transDate;
	private Double balance;
	private Double lastBalance;
	private String authorisedBy;
	private Double creditTotal;
	private Double debitTotal;
	private Double creditSum;
	private Double debitSum;
	
}
