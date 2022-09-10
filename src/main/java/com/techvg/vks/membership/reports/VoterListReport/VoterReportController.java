package com.techvg.vks.membership.reports.VoterListReport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/api/VoterList")
public class VoterReportController {
	@Autowired
	VoterReportService voterReportService;
	
	@GetMapping("/voterReport")
	public ResponseEntity<?> getVoterReport(@RequestParam("membertype")String membertype)
	{ 
		return voterReportService.getVoterListReport(membertype);
	}
}
