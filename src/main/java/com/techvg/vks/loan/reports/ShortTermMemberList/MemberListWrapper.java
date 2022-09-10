package com.techvg.vks.loan.reports.ShortTermMemberList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MemberListWrapper {
	
	private Long memberId;
	private Long id;
	private String name; 
	private Long loanAccountNo;
	private Date loanCloserDate;
	private double loanAmt;  
	private String loanStatus;
	private Double centralGovInterest;
	private Double stateGovInterest;
	private Double distBankInterest;
	
}
