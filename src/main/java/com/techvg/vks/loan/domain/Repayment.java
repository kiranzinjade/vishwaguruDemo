package com.techvg.vks.loan.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="loan_repayment")
public class Repayment extends BaseEntity<String> {
	
	 
	@Column(name = "installment_date")
	Date installmentDate;

	@Column(name = "next_installment_date")
	Date nextInstallmentDate;

	@Column(name = "installment_no")
	Integer installmentNo;
	
	@Column(name = "installment_amount")
	Double installmentAmt;
	
	@Column(name = "opening_principle")
	Double openingPrinciple;
	
	@Column(name = "closing_principle")
	Double closingPrinciple;
	
	@Column(name="principle_paid")
	Double principlePaid;

	@Column(name="total_interest")
	Double totalInterest;
	
	@Column(name="interest_paid")
	Double interestPaid;
	
	@Column(name="balance_interest")
	Double balanceInterest;

	@Column(name = "overdue_amt")
	Double overDueAmt;

	@Column(name = "installment_status")
	String installmentStatus ;//paid,unpaid

	@Column(name = "penalty")
	Double penalty;
	
	@Column(name = "fore_closure_charge")
	Double foreClosureCharge;
	
	@Column(name = "sur_charge")
	Double surCharge;

	@Column(name = "voucher_no")
	Long voucherNo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "loan_details_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public LoanDetails loanDetails;

}
