package com.techvg.vks.accounts.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.techvg.vks.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profit_ledger_list")
public class ProfitDistributionLedger extends BaseEntity<String>  {
	
	@Column(name="acc_head_code")
	String accHeadCode;
	
	@Column(name="amount")
	Double amount;
	
	@Column(name="year")
	Integer year;
	
	  @EqualsAndHashCode.Exclude
	  @ManyToOne(fetch= FetchType.LAZY,cascade=CascadeType.ALL)
	  @JoinColumn(name="ledger_account_id",nullable=false)
	  public LedgerAccounts ledgerAccounts;
		

}
