package com.techvg.vks.accounts.reports.generalledgerreport;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralLedgerReportWrapper {

	  private Double debitAmt;
	    private Double creditAmt;
	    private Double balance;
	    private String voucherNo;
	    private Date transDate;
	    private String remark;
	    private String mode;
	    private String transType;
	    private String accountHeadCode;
	    private Long ledgerAccountId;
}
