package com.techvg.vks.trading.domain;

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
@Table(name = "stock_register")
public class StockRegister extends BaseEntity<String>{
	
	
	@Column(name = "quantity")
	Double quantity;

	@Column(name = "transaction_date")
	Date transactionDate;

	@Column(name = "transaction_type")
	String transType;  // Sales / Purchase / Impairment

	@Column(name = "expiry_date")
	Date expiryDate;

	@Column(name = "batch_no")
	String batchNo;

	@Column(name = "current_stock")
	Double currentStock;

	@Column(name = "current_stock_value")
	Double currentStockValue;

	@Column(name = "voucher_id")
	Long voucherId;
	 
	@EqualsAndHashCode.Exclude
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="product_id",nullable=false)
	public Product product;
}
