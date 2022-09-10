package com.techvg.vks.society.report.assets;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetsReportWrapper {

	private Date transactionDate;
	private String transactionType;
	private Date date;
	private Double amount;
	private Integer purchaseQuantity;
    private Double purchaseValue;
    private Integer salesQuantity;
    private Double saleseValue;
    private Long billNo;
	private Integer balanceQuantity;
	private Double balanceValue;
}
