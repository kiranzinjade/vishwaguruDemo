package com.techvg.vks.trading.reports.SalesRegisterReport;

import java.util.Date;

import com.techvg.vks.trading.reports.PurchaseRegisterReport.PurchaseRegisterReportWrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesRegisterReportWrapper {

	private Long orderNo;
    private Date salesDate;
    private Integer billNo;
    private String fullName;
    private Double quantity;
	private String batchNo;
	private Double sellingPrice;
	private Double totalAmt;
	private String name;
	private Double billAmount;
	private Double balanceAmount;
	private Double paidAmount;
}
