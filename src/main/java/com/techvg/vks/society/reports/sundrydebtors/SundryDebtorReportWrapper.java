package com.techvg.vks.society.reports.sundrydebtors;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SundryDebtorReportWrapper {
	
	private Date date;
	private String particulars;
	private Date transactionDate;
	private Double debit;
	private Double credit;
	private Double balance;
	private String initial;

}
