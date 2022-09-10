package com.techvg.vks.loan.reports.ShortTermMemberList;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface MemberListService {

	public ResponseEntity<?> getShortTermMemberListReport(String loanStatus);
	public byte[] getShortTermMemberListReportForAllMember(String loanStatus);
	
}
