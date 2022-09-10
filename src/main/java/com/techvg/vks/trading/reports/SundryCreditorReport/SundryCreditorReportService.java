package com.techvg.vks.trading.reports.SundryCreditorReport;

import org.springframework.stereotype.Service;

@Service
public interface SundryCreditorReportService {

	byte[] generateSundryCreditorReport(Long CreditorId);

}
