package com.techvg.vks.deposit.reports.DepositLedger;

import java.util.Date;

import com.techvg.vks.deposit.reports.fdreport.FdReportWrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositLedgerReportWrapper {

	private Long accountNo;
	private Date depositDate;
	private Double debitAmount;
	private Double creditAmount;
	private Double balanceAmount;
	private String narration;
	private Integer voucherId;
	private String particulars;
	private Long receiptNo;
	private Double recurringAmount;
	private Integer depositFrequency;
	private Date maturityDate;
	private Double interestEarned;
	private Double maturityAmount;
	private Date depositDate1; //start date
	private Double depositAmount;
	private String depositStatus;
	private Date depositClosingDate;
	private String name;
	private String nomineeName;
	private String guardianName;
	private String gender;
	private Date dob;
	private String nomineeRelation;
	private String nomineePermanentAddr;
	private String parentPermanentAddr;
	private String addressLine1;
	private String addressLine2;
	private Long depositId;
}
