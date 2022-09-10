package com.techvg.vks.membership.reports.VoterListReport;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface VoterReportService {
	String ALL = "ALL";
	ResponseEntity<?> getVoterListReport(String memberType);
	byte[] getvoterListReportForAllMember();
}
