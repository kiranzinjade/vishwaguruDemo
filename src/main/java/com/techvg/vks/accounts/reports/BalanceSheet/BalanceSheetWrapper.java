package com.techvg.vks.accounts.reports.BalanceSheet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceSheetWrapper {

	private Long id;
	private String parentAccHeadCode;
	private Long accountNo;
	private String accountName;
	private Double accBalance;
	private String accHeadCode;
	private String accHeadCode1;
	private String childAccHeadCode;
	private String subChildAccHeadCode;
	private String ledgerCode;
	private String appCode;
	private String classification;
	private String category;
	private Integer level;
	private Integer no;

}
