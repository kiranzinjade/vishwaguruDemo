package com.techvg.vks.membership.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "JoinTableHouse")
@Table(name = "house")
public class House extends BaseEntity<String> {

	@Column(name = "house_number")
	String houseNumber;

	@Column(name = "address_line1")
	String addressLine1;

	@Column(name = "address_line2")
	String addressLine2;

	@Column(name = "city")
	String city;

	@Column(name = "tehsil")
	String tehsil;

	@Column(name = "district")
	String district;

	@Column(name = "state")
	String state;

	@Column(name = "pincode")
	String pincode;

	@Column(name = "is_owned")
	Boolean isOwned;

	@Column(name = "count")
	Integer count;

	@Column(name = "house_area")
	double houseArea;

	@Column(name = "house_valuation")
	double valuation;

	@Column(name = "status")
	String status;

	@Column(name = "address_type")
	String addressType;


	/*
	 * @EqualsAndHashCode.Exclude
	 * 
	 * @ToString.Exclude
	 * 
	 * @ManyToOne(fetch= FetchType.LAZY)
	 * 
	 * @JoinColumn(name="member_id",nullable=false) public Member member;
	 */

}
