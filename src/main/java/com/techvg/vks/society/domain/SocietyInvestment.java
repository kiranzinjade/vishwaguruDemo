package com.techvg.vks.society.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "society_investment")
public class SocietyInvestment extends BaseEntity<String> {

	@Column(name = "board_resolution_no")
	Integer boardResolutionNo;

	@Column(name = "maturity_date")
	Date maturityDate;

	@Column(name = "deposit_date")
	Date depositDate;
	
	@Column(name="deposit_amount")
	Double depositAmount;
	
	@Column(name="maturity_amount")
	Double maturityAmount;
	
	@Column(name="interest_amount")
	Double interestAmount;

	@Column(name = "interest_due_date")
	Date interestDueDate;

	@Column(name = "board_resolution_date")
	Date boardResolutionDate;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "society_investment_id")
	public SocietyInvestmentMaster societyInvestmentMaster;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "societyInvestment", cascade = CascadeType.ALL)
	private Set<SocietyInvestmentDetails> societyInvestmentDetails;
}
