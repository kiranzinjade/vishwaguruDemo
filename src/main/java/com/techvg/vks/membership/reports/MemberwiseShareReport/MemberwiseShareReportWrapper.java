package com.techvg.vks.membership.reports.MemberwiseShareReport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberwiseShareReportWrapper {
	
	private String name;
	private String address;
	private Integer noOfShares;
	private String status;
	private Double value;
	private Long id;
}
