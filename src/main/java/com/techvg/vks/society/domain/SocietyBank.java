package com.techvg.vks.society.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "society_bank_master")
public class SocietyBank extends BaseEntity<String> {

	@Column(name = "bank_name")
	String bankName;
	@Column(name = "branch_name")
	String branchName;
	@Column(name = "ifsc_code")
	String ifsccode;
	@Column(name = "status")
	String status;
	@Column(name = "account_no")
	Long accountNumber;
	
	@Column(name = "account_type")
	String accountType;
	
	 @Column(name = "acc_head_code") 
	  String accHeadCode;
	
	@Column(name = "account_name")
	String accountName;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(fetch= FetchType.LAZY,mappedBy = "societyBank", cascade = CascadeType.ALL)
	private Set<SocietyInvestmentMaster> societyInvestmentMaster;
	
//	@EqualsAndHashCode.Exclude
//	@ToString.Exclude
//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	public DepositType depositType;
	
}
