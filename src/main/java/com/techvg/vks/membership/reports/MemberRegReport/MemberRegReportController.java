package com.techvg.vks.membership.reports.MemberRegReport;

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
@RequestMapping("/api/memberReg")
public class MemberRegReportController {
	
	private final MemberRegReportService memberRegReportService;
	@GetMapping({ "/{memberId}" })
	public ResponseEntity<?> getMemberRegList(@PathVariable("memberId") Long memberId)	{ 
		return memberRegReportService.getMemberRegList(memberId);
	}

	
}
