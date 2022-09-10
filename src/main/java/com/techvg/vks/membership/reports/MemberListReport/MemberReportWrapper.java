package com.techvg.vks.membership.reports.MemberListReport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MemberReportWrapper {
	private String phoneNumber;
	private String address;
	private String name;
	private String status;
}

