package com.techvg.vks.membership.reports.MemberwiseShareReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor

@CrossOrigin(origins = "*")
@RequestMapping("/api/MemberwiseShareList")
public class MemberwiseShareReportController {
	
	@Autowired
	MemberwiseShareReportService memberwiseShareReportService;

	@GetMapping
	public byte[] getMemberwiseShareReport()
	{ 
		return memberwiseShareReportService.getMemberwiseShareListReport();
	}
	
}
