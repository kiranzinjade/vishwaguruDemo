package com.techvg.vks.trading.domain;

import java.util.Date;

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
@Table(name = "trn_sundry_creditors")
public class SundryCreditorTransaction extends BaseEntity<String>{

	@Column(name = "date")
	 Date date;
	
	@Column(name = "transaction_date")
	Date transactionDate;
	
	@Column(name = "debit")
	Double debit;
	
	@Column(name = "credit")
	Double credit;
	
	@Column(name = "particulars")
	String particulars;
	
	@Column(name = "balance")
	Double balance;
	
	 @EqualsAndHashCode.Exclude
	    @ManyToOne(fetch= FetchType.LAZY,cascade=CascadeType.ALL)
		@JoinColumn(name="sundry_creditor_id",nullable=false)
		public SundryCreditor sundryCreditor;
}
