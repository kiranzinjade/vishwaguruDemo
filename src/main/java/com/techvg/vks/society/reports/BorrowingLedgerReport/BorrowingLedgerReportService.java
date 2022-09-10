package com.techvg.vks.society.reports.BorrowingLedgerReport;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;

@Service
public interface BorrowingLedgerReportService {

		public byte[] getBorrowingLedgerListReportforAll(Long bankID)  throws SQLException ,IOException,JRException;


}
