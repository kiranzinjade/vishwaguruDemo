package com.techvg.vks.trading.reports.stockstatementreport;

import java.util.Date;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockStatementWrapper {
	
	private String particulars;
	private Double openingStockQuantity;//
	private Double openingStockValue; //
	private Double closingStockQuantity;//
	private Double closingStockValue;//
	private Double purchaseQuantity;
	private Double purchaseValue;
	private Date date;
	private Double impairmentQuantity;
	private Double salesQuantity;
	private Double total;
	private String itemName;

}
