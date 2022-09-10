package com.techvg.vks.deposit.reports.AccountsOpenedClosed;

import org.springframework.http.ResponseEntity;

public interface AccountsOpenedClosedReportService {

	ResponseEntity<?> getAccountListReport(String accountType);

}
