package com.techvg.vks.accounts.reports.DayBookReport;

import java.io.IOException;
import java.sql.SQLException;

import net.sf.jasperreports.engine.JRException;

public interface DayBookReportService {

	byte[] generateDayBookReport(String transDate) throws SQLException, IOException, JRException;

}
