package com.techvg.vks.loan.domain;

import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.society.domain.LoanProduct;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loan_details")

public class LoanDetails extends BaseEntity<String> {
	
	@Column(name = "loan_account_no")
	Long loanAccountNo;
		
	@Column(name = "loan_amount")
	double loanAmt;
	
	@Column(name = "loan_type")
	String loanType;
	
	@Column(name = "loan_status")
	String loanStatus;
	
	@Column(name = "loan_installment_start_date")
	Date loanInstallStartDate;
	
	@Column(name = "loan_installment_end_date")
	Date loanInstallEndDate;
	
	@Column(name = "installment_frequency")
	String installmentFrequency;//yearly,monthly
	
	@Column(name = "loan_closer_date")
	Date loanCloserDate;
	
	@Column(name = "loan_effective_date")
	Date loanEffectiveDate;
	
	@Column(name = "loan_classfication")
	String loanClassification;//standard,substandard,loss

	@Column(name = "loan_planned_closure_date")
	Date loanPlannedClosureDate;
	
	@Column(name = "society_meeting_no")
	String societyMeetingNo;
	
	@Column(name = "resolution_no")
	Long resolutionNo;
	
	@Column(name = "resolution_date")
	Date resolutionDate;
	
	@Column(name = "cost_of_investment")
	Double costOfInvestment;
	
	@Column(name = "benefiting_area")
	Double benefitingArea;
	
	@Column(name = "dccb_loan_no")
	Long dccbLoanNo;
	
	@Column(name = "mortgage_deed_no")
	Long mortgageDeedNo;
	
	@Column(name = "mortgage_date")
	Date mortgageDate;
	
	@Column(name = "extent_morgage")
	Double extentMorgage;

	@Column(name = "parent_acc_head_code")
	String parentAccHeadCode;

	@Column(name = "loan_acc_name")
	String loanAccName;

	@Column(name = "parent_acc_id")
	Long parentAccId; // Id of ledger account parent

	@Column(name = "disbursement_amt")
	Double disbursementAmt;

	@Column(name = "disbursement_status")
	String disbursementStatus;//full,partial

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude 
	public Member member;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loan_demand_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public LoanDemand loanDemand;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loan_product_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public LoanProduct loanProduct;
 
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "loanDetails", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<Disbursement> disbursements = new HashSet<>();
	
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "loanDetails", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<Repayment> repayment;

    @OneToOne(mappedBy = "loanDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private ShortTermSubsidy shortTermSubsidy;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "loanDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Amortization>  amortizations;
   
    @EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "loanDetails", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<LoanDocument> loanDocument;

	public void addToDisbursement(Disbursement disbursement) {
		this.disbursements.add(disbursement);
	}
}
