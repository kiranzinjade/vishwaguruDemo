package com.techvg.vks.deposit.reports.SavingsAccountLedger;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

import net.sf.jasperreports.engine.JRException;

public interface SavingAccountReportService {

	ResponseEntity<?> getSavingAccountListReport(Long accountNo);

}
