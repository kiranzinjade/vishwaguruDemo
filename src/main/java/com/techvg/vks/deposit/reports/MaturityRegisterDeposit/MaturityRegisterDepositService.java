package com.techvg.vks.deposit.reports.MaturityRegisterDeposit;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface MaturityRegisterDepositService {

	ResponseEntity<?> getMaturityReport(String fromDate, String toDate);

	 byte[] CreatingMaturityReport(String fromDate, String toDate);
	

}
