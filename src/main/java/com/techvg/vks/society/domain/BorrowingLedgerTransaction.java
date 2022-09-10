package com.techvg.vks.society.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "borrowing_ledger_trans")
public class BorrowingLedgerTransaction extends BaseEntity<String>{
	
	@Column(name="trans_date")
	Date transactionDate;
	
	@Column(name="particulars")
	String particulars;
	
	@Column(name="debit")
	Double debit;
	
	@Column(name="credit")
	Double credit;
	
	@Column(name="balance")
	Double balance;
	
	@Column(name="initials")
	String initials;
	
	@Column(name="remarks")
	String remarks;
	
	@Column(name="no_of_days")
	Integer noOfDays;

	@Column(name="total_interest")
	Double totalInterest;
	
	@Column(name="interest_paid")
	Double interestPaid;
	
	@Column(name="interest_due")
	Double interestDue;
	
	@EqualsAndHashCode.Exclude
	@ManyToOne(fetch= FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="borrowing_ledger_id")
	public BorrowingLedger borrowingLedger;

}
