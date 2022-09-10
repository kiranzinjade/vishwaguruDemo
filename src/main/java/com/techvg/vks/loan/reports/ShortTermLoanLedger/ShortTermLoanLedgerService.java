package com.techvg.vks.loan.reports.ShortTermLoanLedger;

import org.springframework.stereotype.Service;

@Service
public interface ShortTermLoanLedgerService {
	public byte[] getShortTermLoanListReport();

	public byte[] getShortTermLoanListReport(Long loanId);

}
