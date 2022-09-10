package com.techvg.vks.society.domain;

import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.loan.domain.CropLoanDemand;
import com.techvg.vks.loan.domain.LoanDemand;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.domain.LoanWatap;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="loan_product")
public class LoanProduct extends BaseEntity<String>{
	
	@Column(name="product_type")
	private String productType;
	
	@Column(name="product_name")
	private String productName;
	
	@Column(name="status")	
	private String status;
	
	@Column(name="valid_from")    
	private Date validFrom;
	
	@Column(name="valid_to")    
	private Date validTo;
	
	@Column(name="duration")    
	private Integer duration;//in month
	
	@Column(name="surcharge")    
	private Double surcharge;
	
	@Column(name="interest_rate")    
	private Double interestRate;
	
	@Column(name="penalty_interest")
	private Double penaltyInterest;
	
	@Column(name="no_of_installment")
	private Integer numberOfInstallment;	
	
	@Column(name = "resolution_no")
	private String resolutionNo;

	@Column(name = "resolution_date")
	private Date resolutionDate;

	@Column(name="no_of_disbursement")
	private Integer noOfDisbursement;
	
	@Column( name="last_date_of_repayment")
	private Date lastDateOfRepayment;
	
	@Column(name="max_loan_amt")
	private Double maxLoanAmount;

	@Column(name="unit_size")
	private String unitSize;

	@Column(name = "parent_acc_head_code")
	private String parentAccHeadCode;

	@Column(name = "parent_acc_head_id")
	private Long parentAccHeadId;

	@Column(name = "acc_head_code")
	private String accHeadCode;

	@Column(name="loan_number")
	private Long loanNumber;

	@Column(name="borrowing_interest_rate")
	private Double borrowingInterestRate;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(fetch = FetchType.LAZY,mappedBy="loanProduct",cascade=CascadeType.ALL)
	private Set<LoanDetails> loanDetails;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(fetch = FetchType.LAZY,mappedBy="loanProduct",cascade=CascadeType.ALL)
	private Set<LoanDemand> loanDemand;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy="loanProduct", fetch= FetchType.LAZY)
	private Set<LoanWatap> loanWatap;

}
