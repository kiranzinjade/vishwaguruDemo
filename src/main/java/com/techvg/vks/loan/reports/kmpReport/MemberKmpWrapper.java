package com.techvg.vks.loan.reports.kmpReport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberKmpWrapper {
	
	private Long kmpNo;
	private String societyName;
	private Long memberId;
	private String memberFullName;
	private String memMobileNo;
	private Double shareAmount;
	private String  jindagiPatrakNo;
	private Double jindagiAmount;
	private Integer syear;
	private Integer eyear;
	private Double totalLandArea;
	private Set<KmpReportWrapper> kmpReportWrapper;
}
