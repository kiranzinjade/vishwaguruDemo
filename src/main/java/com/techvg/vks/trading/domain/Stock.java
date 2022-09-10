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
@Table(name = "stock")

public class Stock extends BaseEntity<String>{
	
	@Column(name = "opening_stock_quantity")
	Double openingStockQuantity;

	@Column(name = "opening_stock_value")
	Double openingStockValue;

	@Column(name = "closing_stock_quantity")
	Double closingStockQuantity;

	@Column(name = "closing_stock_value")
	Double closingStockValue;

	@Column(name = "stock_date")
	Date stockDate;

	@EqualsAndHashCode.Exclude
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="product_id",nullable=false)
	public Product product;
}
