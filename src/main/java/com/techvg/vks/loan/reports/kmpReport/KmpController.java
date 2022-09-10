package com.techvg.vks.loan.reports.kmpReport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/api/kmp")
public class KmpController {
	
	private final KmpService kmpservice;
   	
	@GetMapping({ "/member/{year}" })
	public ResponseEntity<List<MemberKmpWrapper>> geKmpReport(@PathVariable("year") int year)
	{
		List<MemberKmpWrapper> memberKmpWrapperList = kmpservice.getMemberKmpReport(year);

		return new ResponseEntity<>(memberKmpWrapperList, HttpStatus.OK);
	}

	@GetMapping({ "/society/{year}" })
	public ResponseEntity<List<SocietyKmpWrapper>> geSocietyKmpReport(@PathVariable("year") int year)
	{
		List<SocietyKmpWrapper> societyKmpWrapperList = kmpservice.getSocietyKmpReport(year);

		return new ResponseEntity<>(societyKmpWrapperList, HttpStatus.OK);
	}

}
