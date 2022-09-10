package com.techvg.vks.loan.reports.kmpReport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KmpReportWrapper {

	private String landType;
	private String gatNo;
	private Integer totalAreaHector;
	private Integer totalAreaR;

	private String cropName;
	private String season;
	private Double maxHectorAmount;
}
