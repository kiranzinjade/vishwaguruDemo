package com.techvg.vks.loan.reports.kmpReport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocietyKmpWrapper {
	private Long memberId;
	private String memberFullName;
	private String cropName;
	private String kmpNo;
	private String jindagiPatrakNo;
	private Double eligibleLoanAmount;
	private Double cropLand;
	private Double totalLand;
	private Integer syear;
	private Integer eyear;
}
