package com.techvg.vks.membership.reports.MemberwiseShareReport;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface MemberwiseShareReportService {
	
	public byte[] getMemberwiseShareListReport();
	public byte[] getmemberwiseShareListReportForAllMember();

}
