package com.techvg.vks.membership.reports.MemberListReport;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface MemberReportService {
	String ALL = "ALL";
	ResponseEntity<?> getMemberListReport(String memberType);
	byte[] getmemberListReportForAllMember();

	
}

