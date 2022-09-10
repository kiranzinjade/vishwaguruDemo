package com.techvg.vks.membership.reports.MemberNotice;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/api/MemberNotice")
public class MemberNoticeController {
	
	private final MemberNoticeService memberNoticeService;
	
	@GetMapping(path = { "/{memberId}" })
	public byte[] getMemberNoticeReport(@PathVariable("memberId")Long memberId)
	{ 
		return memberNoticeService.getMemberNoticeReport(memberId);
	}
	

}
