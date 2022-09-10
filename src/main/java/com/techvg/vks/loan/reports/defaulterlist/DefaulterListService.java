package com.techvg.vks.loan.reports.defaulterlist;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface DefaulterListService {
	//public ResponseEntity<?> getDefaulterListReport();
	
	
	public byte[] getdefaulterListReportForAllMember(String loanType);

	public byte[] getDefaulterListReport(String loanType);
	
}
