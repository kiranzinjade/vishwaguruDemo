package com.techvg.vks.deposit.reports.MonthlyInterestFD;

import java.util.Date;

import com.techvg.vks.deposit.reports.fdreport.FdReportWrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MonthlyInterestFDWrapper {
	
	private String name;
	private Long accountNo;
	private String accountType;
	private Long receiptNo;
	private Date depositDate;
    private Date maturityDate;
	private Double interestEarned;
	private Double depositAmount;
	private Long depositId;
}
