package com.techvg.vks.society.reports.sundrydebtors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/sundryDebtorsReport")
public class SundryDebtorReportController {

	private final SundryDebtorReportService sundryDebtorReportService;

	@GetMapping({ "/{sundryDebtorId}" })
	public ResponseEntity<?> getSundryDebtorsReport(@PathVariable("sundryDebtorId") Long sundryDebtorId)
	{ 
		return sundryDebtorReportService.getSundryDebtorsReport(sundryDebtorId);
	}


}
