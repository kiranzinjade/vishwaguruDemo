package com.techvg.vks.trading.domain;

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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sundry_debtor")
public class SundryDebtor extends BaseEntity<String>{

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
	
	 @Column(name = "daybook_id")
	 Long daybookId;
	
	 @Column(name = "voucher_id")
	 Long voucherId;
	 
	 @EqualsAndHashCode.Exclude
	    @ManyToOne(fetch= FetchType.LAZY,cascade=CascadeType.ALL)
		@JoinColumn(name="member_id",nullable=false)
		public Member member;
	 
	 @EqualsAndHashCode.Exclude
	    @ToString.Exclude
	    @OneToMany(cascade = CascadeType.ALL)
	    @JoinColumn(name = "sundry_debtor_id")
	    private Set<SundryDebtorTransaction> sundryDebtorTransaction;
}
