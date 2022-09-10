package com.techvg.vks.loan.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.membership.domain.Land;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.society.domain.CropRegistration;
import com.techvg.vks.society.domain.LoanProduct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loan_watap")
public class LoanWatap extends BaseEntity<String> {

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

	/// extra fields for mid and long loan

	@Column(name = "status")
	String status;

	@Column(name = "slot")
	Integer slot;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public Member member;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loan_demand_id")
	private LoanDemand loanDemand;

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
	@JoinColumn(name = "loan_product_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private LoanProduct loanProduct;

}
