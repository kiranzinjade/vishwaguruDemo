package com.techvg.vks.share.controller;

import com.techvg.vks.share.service.ShareDividendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/api/share/dividend")
@CrossOrigin(origins="*")
@RequiredArgsConstructor
public class ShareDividendController {
private final ShareDividendService sharedividendservice;

	@GetMapping("/calculate")
	public ResponseEntity<?> calculate()
	{ 	
	return sharedividendservice.calculateShareDividend();	
	}
	
	@GetMapping("/approve")
	public ResponseEntity<?> approve()
	{ 
		return sharedividendservice.approveShareDividend();
	}
	
}
