package com.techvg.vks.trading.reports.SalesReturnReport;

import java.util.Date;

import com.techvg.vks.trading.reports.SalesRegisterReport.SalesRegisterReportWrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesReturnReportWrapper {


	private Long orderNo;
    private Date returnDate;
    private Integer billNo;
    private String fullName;
    private Double returnQuantity;
	private String batchNo;
	private Double price;
	private Double totalPrice;
	private String name;
	private Double returnAmt;
	private Double balanceAmount;
	private String returnReason;
}
