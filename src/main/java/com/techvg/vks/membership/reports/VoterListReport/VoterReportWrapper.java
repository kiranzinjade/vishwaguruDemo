package com.techvg.vks.membership.reports.VoterListReport;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VoterReportWrapper {
	private String phoneNumber;
	private String address;
	private String name;
	private String status;
}
