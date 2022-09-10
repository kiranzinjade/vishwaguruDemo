package com.techvg.vks.loan.reports.RepaymentCollectionRegister;

import java.util.Date;

import com.techvg.vks.loan.reports.defaulterlist.DefaulterListWrapper;
import com.techvg.vks.membership.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepaymentCollectionRegisterWrapper {

	private Long member_id;
	private Long id;
	private String name; 
	private Date insatllmentDate;
	private double closingPrinciple;  
	//private double totalLoanInterest;  
	private double loanAmt;  
	private Date loanPlannedClosureDate;  
	private Member member;
	private Double installmentAmt;
	private Double principlePaid;
	private Double interestPaid;
	private Double balanceInterest;
	private String loanClassfication;
	private String loanType;
}
