package com.techvg.vks.deposit.reports.fdreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor

@CrossOrigin(origins = "*")
@RequestMapping("/api/fdReport")
public class FdReportController {
	
	@Autowired
	FdReportService fdReportService;

	@GetMapping
	public byte[] getFdReport()
	{ 
		return fdReportService.getFdlistReport();
	}
	@GetMapping({ "/{depositId}" })
	public byte[] getFdReport(@PathVariable("depositId") Long depositId )
	{ 
		return fdReportService.getFdlistReport(depositId);
	}
}
