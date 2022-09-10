package com.techvg.vks.society.domain;

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
@Entity
@Table(name="loan_charges")

public class LoanCharges extends BaseEntity<String> {
	
	@Column(name="charges_name")
	String chargesName;
	
	@Column(name="charges_value")
	Double chargesValue;

	@Column(name="charges_type")
	String chargesType;

	@Column(name="charges_category")
	String chargesCategory;

	@Column(name="description")
	String description;
	
	@Column(name="loan_type")
	String loanType;
	
}
