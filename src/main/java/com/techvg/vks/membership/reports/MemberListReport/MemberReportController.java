package com.techvg.vks.membership.reports.MemberListReport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/api/MemberList")
public class MemberReportController {

	@Autowired
	MemberReportService memberReportService;
	
	@GetMapping("/memberReport")
	public ResponseEntity<?> getMemberReport(@RequestParam("membertype")String membertype)
	{ 
		return memberReportService.getMemberListReport(membertype);
	}
	
}


