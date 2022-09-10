package com.techvg.vks.society.report.bankbook;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankbookReportWrapper {

	private String bankName;
	private String accountType;
	private Long accountNumber;
	private Date date;
	private String particulars;
	private Date transactionDate;
	private Double debit;
	private Double credit;
	private Double balance;
	private Double total;
	private String narration;
	private String initial;
	
}
