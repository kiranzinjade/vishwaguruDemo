package com.techvg.vks.share.reports.ShareCapitalLedger;

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
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/sharecapitalledger")
public class ShareCapitalLedgerController {

	private final ShareCapitalLedgerService  shareCapitalLedgerService;
	
	@GetMapping({ "/{memberId}" })
	public byte[] generateShareCapitalLedgerReport(@PathVariable("memberId") Long memberId) {
		log.debug("REST request to get Transaction Date : {}", memberId);
	return shareCapitalLedgerService.generateShareCapitalLedgerReport(memberId);
	}
}
