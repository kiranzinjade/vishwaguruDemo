package com.techvg.vks.loan.reports.defaulterlist;


import java.util.Date;

import com.techvg.vks.membership.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaulterListWrapper {

	private Long member_id;
	private Long id;
	private String name; 
	private Date disbursementDate;
	private double closingPrinciple;  
	//private double totalLoanInterest;  
	private double loanAmt;  
	private Date loanPlannedClosureDate;  
	private Member member;
	
}
