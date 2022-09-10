package com.techvg.vks.loan.domain;

import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.society.domain.CropRegistration;
import com.techvg.vks.society.domain.LoanProduct;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "crop_loan_demand")
public class CropLoanDemand extends BaseEntity<String> {

	@Column(name = "year")
    Integer year;

	@Column(name = "season")
	String season;

	@Column(name = "kmp_status")
	Boolean kmpStatus = false;

	@Column(name = "society_kmp_status")
	Boolean societyKmpStatus = false;

	@Column(name = "kmp_approval_status")
	Boolean kmpApprovalStatus = false;
	
	@Column(name = "loan_registration_status")
	Boolean loanRegistrationStatus = false;

	@Column(name = "tharav_no")
	String tharavNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "crop_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public CropRegistration crop;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(fetch = FetchType.LAZY,mappedBy="cropLoanDemand",cascade=CascadeType.ALL)
	private Set<LoanDemand> loanDemand=new HashSet<>();

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="loan_product_id")
	private LoanProduct loanProduct;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kmp_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public KMP kmp;

}
