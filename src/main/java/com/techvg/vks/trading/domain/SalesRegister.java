package com.techvg.vks.trading.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sales_register")

public class SalesRegister extends BaseEntity<String>{

	@Column(name="quantity")
	Integer quantity;

	@Column(name = "expiry_date")
	Date expiryDate;

	@Column(name = "batch_no")
	String batchNo;

	@Column(name = "selling_price")
	Double sellingPrice;

	@Column(name = "cgst_amt")
	Double cgstAmt;

	@Column(name = "sgst_amt")
	Double sgstAmt;

	@Column(name = "igst_amt")
	Double igstAmt;

	@Column(name = "total_amt")
	Double totalAmt;

	@EqualsAndHashCode.Exclude
	@ManyToOne(fetch= FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="product_id",nullable=false)
	public Product product;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL,fetch= FetchType.LAZY)
	@JoinColumn(name = "sales_order_id",  referencedColumnName = "id")
	private SalesOrder salesOrder;

	
	
}
