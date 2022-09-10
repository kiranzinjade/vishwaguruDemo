package com.techvg.vks.loan.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class ListingPageDto {
	

	private String loanAccountNo;

	private double loanAmt;

	private Date loanInstallStartDate;

	private String name;
	
	private String loanType;

	public ListingPageDto(String loanAccountNo, double loanAmt, Date loanInstallStartDate, String name) {
		super();
		this.loanAccountNo = loanAccountNo;
		this.loanAmt = loanAmt;
		this.loanInstallStartDate = loanInstallStartDate;
		this.name = name;
	}

}
