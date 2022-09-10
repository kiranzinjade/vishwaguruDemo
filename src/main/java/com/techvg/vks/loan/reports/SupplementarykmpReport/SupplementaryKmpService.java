package com.techvg.vks.loan.reports.SupplementarykmpReport;

import org.springframework.http.ResponseEntity;

public interface SupplementaryKmpService {

	ResponseEntity<?> getSupplementaryKmpReport(Long loanDemandId);
	byte[] CreatingSupplementaryKmpReport(Long loanDemandId);
}
