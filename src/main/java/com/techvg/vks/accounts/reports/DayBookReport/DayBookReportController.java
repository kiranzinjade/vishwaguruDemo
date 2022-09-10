package com.techvg.vks.accounts.reports.DayBookReport;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/api/daybookreport")
public class DayBookReportController {

	private final DayBookReportService  dayBookReportService;
	
	@GetMapping({ "/{transDate}" })
	public byte[] generateDayBookReport(@PathVariable("transDate") String  transDate) throws SQLException, IOException, JRException {
		log.debug("REST request to get Transaction Date : {}", transDate);
	return dayBookReportService.generateDayBookReport(transDate);
	}
}
