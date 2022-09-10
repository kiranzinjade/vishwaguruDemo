package com.techvg.vks.accounts.reports.DayBookReport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayBookReportWrapper {

	private Double creditCashAmt;
	private Double debitCashAmt;
	private Double creditTransferAmt;
	private Double debitTransferAmt;
	private String transType;
	
}
