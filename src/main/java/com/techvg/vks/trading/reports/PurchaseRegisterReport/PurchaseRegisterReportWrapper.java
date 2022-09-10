package com.techvg.vks.trading.reports.PurchaseRegisterReport;

import java.util.Date;

import com.techvg.vks.loan.reports.LoanDisbursementRegister.LoanDisbursementRegisterWrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRegisterReportWrapper {
	
	private Long orderNo;
    private Date purchaseDate;
    private Integer billNo;
    private String ownerName;
    private Double quantity;
	private String batchNo;
	private Double buyingPrice;
	private Double totalAmt;
	private String name;
	private Double billAmount;
	private Double balanceAmount;
	private Double paidAmount;


}
