package com.techvg.vks.loan.reports.LoanDisbursementRegister;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDisbursementRegisterWrapper {

	private String name;
	private String address;
	private double loanAmt;
	private Date loanEffectiveDate;
	private String loanType; 
}
