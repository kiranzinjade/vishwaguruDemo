package com.techvg.vks.accounts.reports.CashBookReport;

import org.springframework.stereotype.Service;

@Service
public interface CashBookReportService {

	byte[] generateCashBookReport(String transDate);

}
