package com.techvg.vks.loan.reports.AcknowledgmentOfDebt;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AcknowledgmentOfDebtService {

	byte[] creatingAcknowledgmentOfDebtReport(String endYearDate);
	ResponseEntity<?> getAcknowledgmentOfDebtReport(String endYearDate);
	
}
