package com.techvg.vks.deposit.reports.fdreport;

import java.util.Date;


import com.techvg.vks.membership.reports.MemberwiseShareReport.MemberwiseShareReportWrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FdReportWrapper {
	
	private String name;
	private Long accountNo;
	private String accountType;
	private Long receiptNo;
	private Integer durationDays;
	private Date depositDate;
	private Date maturityDate;
	private Double interest;
	private Double depositAmount;
	private Double maturityAmount;
	private Long depositId;
	private String depositInWords;
	

}
