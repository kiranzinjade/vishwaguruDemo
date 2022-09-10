package com.techvg.vks.loan.domain;

import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.membership.domain.Land;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.society.domain.CropRegistration;
import com.techvg.vks.society.domain.LoanProduct;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loan_demand")
public class LoanDemand  extends BaseEntity<String> {
	
		
	@Column(name = "season")
	String season;
	
	@Column(name = "year")
    Integer year;
	
	@Column(name = "loan_amount")
	Double loanAmount;
	
	@Column(name = "crop_land_area_hector")
	Integer cropLandAreaHector;

	@Column(name = "crop_land_area_r")
	Integer cropLandAreaR;

	@Column(name = "max_allowed")
	Double maxAllowed;

	@Column(name = "adjusted_demand")
	Double adjustedDemand;
	
	///extra fields for mid and long loan
	
	@Column(name = "annual_income")
	Double annualIncome;
	
	@Column(name="status")
	String status;
	
	@Column(name = "cost_of_investment")
	Double costOfInvestment;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public Member member;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loan_product_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public LoanProduct loanProduct;
	

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToOne(mappedBy="loanDemand", fetch= FetchType.LAZY)
	private LoanWatap LoanWatap;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "land_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public Land land;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "crop_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public CropRegistration crop;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "crop_loan_demand_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public CropLoanDemand cropLoanDemand;
	
}
