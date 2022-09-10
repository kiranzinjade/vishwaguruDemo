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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techvg.vks.base.BaseEntity;
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
@Table(name = "deposit_ledger")
public class DepositLedger extends BaseEntity<String> {

	@Column(name = "deposit_date")
	Date depositDate;

	@Column(name = "account_no")
	Long accountNo;

	@Column(name = "debit_amount")
	Double debitAmount;

	@Column(name = "credit_amount")
	Double creditAmount;

	@Column(name = "balance_amount")
	Double balanceAmount;

	@Column(name = "narration")
	String narration;

	@Column(name = "voucher_id")
	Integer voucherId;

	@Column(name = "particulars")
	String particulars;
	
	@Column(name = "date")
	Date date;

	@Column(name = "day_book_created")
	Boolean dayBookCreated;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deposit_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude 
	public Deposit deposit;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "saving_account_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude 
	public SavingAccount savingAccount;
}
