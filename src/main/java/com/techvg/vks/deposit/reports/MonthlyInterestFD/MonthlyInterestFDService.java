package com.techvg.vks.deposit.reports.MonthlyInterestFD;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface MonthlyInterestFDService {

	/*
	 * byte[] getFDMember(Long memberId); byte[] getFDMemberReport();
	 */
	ResponseEntity<?> getFDMember(Long memberId);

}
