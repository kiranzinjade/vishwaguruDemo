package com.techvg.vks.deposit.reports.MaturityRegisterDeposit;

import java.util.Date;

import com.techvg.vks.deposit.reports.fdreport.FdReportWrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaturityRegisterDepositWrapper {
	
	
	private Date depositDate;
	private Date maturityDate;
	private Double maturityAmount;
	private Long depositId;
	private Double amountPaid;
	private Double balance;
	

}
