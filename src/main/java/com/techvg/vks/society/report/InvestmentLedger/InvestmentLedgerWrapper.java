package com.techvg.vks.society.report.InvestmentLedger;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvestmentLedgerWrapper {

	private String bankName;
	private String nomenclature;
	private String accountType;
	private Integer period;
	private Double interest;
	private Integer boardResolutionNo;
	private Date boardResolutionDate;
	private Date depositDate;
	private Date maturityDate;
	private Double depositAmount;
	private Double maturityAmount;
	private Double interestAmount;
	private Date depositDate1;
	private String particulars;
	private Double debitAmount;
	private Double creditAmount;
	private Double balanceAmount;
	private Double interestAmount1;
	private Date interestDate;
}
