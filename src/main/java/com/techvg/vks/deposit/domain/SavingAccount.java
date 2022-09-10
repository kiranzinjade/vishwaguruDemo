package com.techvg.vks.deposit.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.society.domain.DepositAccount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "saving_account")
public class SavingAccount extends BaseEntity<String> {
	
	@Column(name = "account_opening_date")
	Date accountOpeningDate;
	
	@Column(name = "account_closing_date")
	Date accountClosingDate;
	
	@Column(name = "balance")
	Double balance;

	@Column(name = "account_no")
	Long accountNo;
	
	@Column(name = "status")
	String status;

	@Column(name = "parent_acc_head")
	String parentAccHead;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	public Member member;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "deposit_account_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude 
	public DepositAccount depositAccount;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "savingAccount", cascade = CascadeType.ALL)
	private Set<DepositLedger> depositLedger;

}
