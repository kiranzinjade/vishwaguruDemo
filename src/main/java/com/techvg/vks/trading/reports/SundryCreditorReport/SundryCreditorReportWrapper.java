package com.techvg.vks.trading.reports.SundryCreditorReport;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SundryCreditorReportWrapper {
	
	private Date date;
	private Long dayBookId;
	private Long voucherId;
	private String particulars;
	private Double debit;
	private Double credit;
	private Double balance;
	private Date transDate;
	private String initials;
	private String name;

}
