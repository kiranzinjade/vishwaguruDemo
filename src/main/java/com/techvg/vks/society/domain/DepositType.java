package com.techvg.vks.society.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="deposit_type")
public class DepositType extends BaseEntity<String> {
	
	
	@Column(name = "account_type")
	String accountType;

	@Column(name = "description")
	String description;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "depositType", cascade = CascadeType.ALL)
	private Set<DepositAccount> depositAccounts;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(fetch= FetchType.LAZY,mappedBy = "depositType", cascade = CascadeType.ALL)
	private Set<SocietyInvestmentMaster> societyInvestmentMaster;
	
//	@EqualsAndHashCode.Exclude
//	@ToString.Exclude
//	@OneToMany(fetch= FetchType.LAZY,mappedBy = "depositType", cascade = CascadeType.ALL)
//	private Set<SocietyBank> societyBank;
}
