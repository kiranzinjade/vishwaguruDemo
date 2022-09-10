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
@Table(name = "sales_return")
public class SalesReturn extends BaseEntity<String> {


	@Column(name = "bill_no")
	Integer billNo;

	@Column(name = "return_date")
	Date returnDate;

	@Column(name = "return_amt")
	Double returnAmt;

	@Column(name = "balance_amount")
	Double balanceAmount;

	@Column(name = "paid_amount")
	Double paidAmount;
	  
	@Column(name = "return_mode")
	String returnMode;     

	@EqualsAndHashCode.Exclude
	@ManyToOne(fetch= FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="sales_order_id",nullable=false)
	public SalesOrder salesOrder;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(fetch= FetchType.LAZY,cascade=CascadeType.ALL)
	public Set<SalesReturnDetails> salesReturnDetails;

}
