package com.techvg.vks.society.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "society_investment_mst")
public class SocietyInvestmentMaster extends BaseEntity<String> {

	@Column(name = "nomenclature")
	String nomenclature;

	@Column(name = "period")
	Integer period;

	@Column(name = "interest")
	Double interest;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "societyInvestmentMaster", cascade = CascadeType.ALL)
	private Set<SocietyInvestment> societyInvestments;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public SocietyBank societyBank;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public DepositType depositType;
}
