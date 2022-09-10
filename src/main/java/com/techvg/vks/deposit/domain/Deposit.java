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
@Table(name = "deposits")
public class Deposit extends BaseEntity<String>  {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude 
	public Member member;

	@Column(name = "receipt_no")
	Long receiptNo;
	
	@Column(name = "account_no")
	Long accountNo;

	@Column(name = "accrual_account_no")
	Long accrualAccountNo;

	@Column(name = "recurring_amount")
	Double recurringAmount;
	
	@Column(name = "deposit_frequency")
	Integer depositFrequency;

	@Column(name = "maturity_date")
	Date maturityDate;

	@Column(name = "interest_earned")
	Double interestEarned;
	
	@Column(name = "maturity_amount")
	Double maturityAmount;
	
	@Column(name = "deposit_date")
	Date depositDate; //start date

	@Column(name = "deposit_amount")
	Double depositAmount;
	
	@Column(name="deposit_status")
	String depositStatus;
	
	@Column(name="deposit_closing_date")
	Date depositClosingDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deposit_account_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude 
	public DepositAccount depositAccount;

	@Column(name="reinvestment_status")
	Boolean reinvestmentStatus;

	@Column(name = "acc_head")
	String accHead;

	@Column(name = "parent_acc_head")
	String parentAccHead;
 
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "deposit", cascade = CascadeType.ALL)
	private Set<DepositLedger> depositLedger;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "deposit", cascade = CascadeType.ALL)
	private Set<DepositAccrual> depositAccruals;
}
