package com.techvg.vks.loan.reports.ShortTermMemberList;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/loan/short/MemberList")

public class MemberListController {
	
	@Autowired
	MemberListService memberListService;
	
	@GetMapping({ "/{loanStatus}" })
	public ResponseEntity<?> getShortTermMemberListReport(@PathVariable("loanStatus") String loanStatus)
	{ 
		return memberListService.getShortTermMemberListReport(loanStatus);
	}
	

}
