package com.techvg.vks.loan.reports.SocietykmpReport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocietyKmpReportWrapper {	
	private Long id;
	private Long memberId;
	private String memberFullName;
	private String cropName;
	private String cropSeason;
	private Integer cropAreaHector;
	private Integer cropAreaR;
	private String gatNo;
	private Double eligibleLoanAmount;
}
