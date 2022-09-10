package com.techvg.vks.loan.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="loan_amortization")

public class Amortization extends BaseEntity<String>{
	
	@Column(name = "installment_date")
	Date installmentDate;  //loandetails loan start date for first time after +1 year
	
	@Column(name = "outstanding_principle")
	Double outstandingPrinciple; //loandetails loan Amount
	
	@Column(name = "interest_amount")
	Double interestAmt;   //loanAmount * roi 
	
	@Column(name = "principle_EMI")
	Double principleEMI;   
	
	@Column(name = "total_amount")
	Double totalAmt;  //installmentAmt + interestAmt

	@Column
	String paymentStatus; // Paid, Overdue

	@Column(name = "bal_principle_amount")
	Double balPrincipleAmt;

	@Column(name = "bal_interest_amount")
	Double balInterestAmt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loan_details_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public LoanDetails loanDetails;
	
}
