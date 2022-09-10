package com.techvg.vks.deposit.reports.MonthlyInterestFD;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.membership.reports.MemberRegReport.MemberRegReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/MonthlyInterestFD")

public class MonthlyInterestFDController {
	
	private final MonthlyInterestFDService monthlyInterestFDService;
	
	@GetMapping({ "/{memberId}" })
	public ResponseEntity<?> getFDMember(@PathVariable("memberId") Long memberId)	{ 
		return monthlyInterestFDService.getFDMember(memberId);
	}

}
