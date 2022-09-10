package com.techvg.vks.loan.reports.loandisbursement;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface LoanDisbursementReportService {

	public ResponseEntity<?> getLoanDisbursement(String loanType);
	public ResponseEntity<?> getLoanDisbursement(Long loanId);
	public byte[] getAllLoanDisbursementList(List<LoanDisbursementReportWrapper> loanDisbursementWrapperlist);

}
