package com.techvg.vks.society.reports.sundrydebtors;

import org.springframework.http.ResponseEntity;

public interface SundryDebtorReportService {

	ResponseEntity<?> getSundryDebtorsReport(Long sundryDebtorId);

}
