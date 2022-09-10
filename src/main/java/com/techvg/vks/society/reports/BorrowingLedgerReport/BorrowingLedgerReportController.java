package com.techvg.vks.society.reports.BorrowingLedgerReport;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequiredArgsConstructor

@CrossOrigin(origins = "*")

@RequestMapping("/api/borrowingLedgerList")
public class BorrowingLedgerReportController {
	

	private final BorrowingLedgerReportService borrowingLedgerReportService;
	
	@GetMapping({ "/{bankId}" })
	public byte[] getBorrowingLedgerReport(@PathVariable("bankId") Long bankId)  throws SQLException ,IOException,JRException
	{ 
		return borrowingLedgerReportService.getBorrowingLedgerListReportforAll(bankId);
	}

}
