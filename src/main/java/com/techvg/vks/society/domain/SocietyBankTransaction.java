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
@Table(name = "society_bank_trans")
public class SocietyBankTransaction extends BaseEntity<String> {

	@Column(name = "date")
	Date date;
	
	@Column(name = "particulars")
	String particulars;
	
	@Column(name = "transaction_date")
	Date transactionDate;
	
	@Column(name = "debit")
	Double debit;
	
	@Column(name = "credit")
	Double credit;
	
	@Column(name = "balance")
	Double balance;
	
	@Column(name = "narration")
	String narration;
	
	@Column(name = "initial")
	String initial;

	@Column(name = "voucher_no")
	Long voucherNo;

	@Column(name = "trans_type") //debit or credit
	String transType;

	@EqualsAndHashCode.Exclude
    @ManyToOne(fetch= FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="bank_id",nullable=false)
	public SocietyBank societyBank;
	
	
}
