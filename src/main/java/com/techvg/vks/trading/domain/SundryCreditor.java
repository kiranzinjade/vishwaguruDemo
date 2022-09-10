package com.techvg.vks.trading.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sundry_creditor")
public class SundryCreditor extends BaseEntity<String> {
	
	@Column(name="date")
	Date date;
	
	@Column(name="day_book_id")
	Long dayBookId;
	
	@Column(name="voucher_no")
	Long voucherId;
	
	@Column(name="particulars")
	String particulars;
	
	@Column(name="debit")
	Double debit;
	
	@Column(name="credit")
	Double credit;
	
	@Column(name="balance")
	Double balance;
	
	@Column(name="trans_date")
	Date transDate;
	
	@Column(name="initials")
	String initials;
	
	@EqualsAndHashCode.Exclude
    @ManyToOne(fetch= FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="vendor_id",nullable=false)
	public VendorRegister vendor;
	
	 @EqualsAndHashCode.Exclude
	    @ToString.Exclude
	    @OneToMany(cascade = CascadeType.ALL)
	    @JoinColumn(name = "sundry_creditor_id")
	    private Set<SundryCreditorTransaction> sundryCreditorTransaction;

}
