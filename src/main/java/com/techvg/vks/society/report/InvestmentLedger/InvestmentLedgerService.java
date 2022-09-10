package com.techvg.vks.society.report.InvestmentLedger;

import org.springframework.stereotype.Service;

@Service
public interface InvestmentLedgerService {

	public byte[] getInvestmentDetails(Long society_investment_id);

	public byte[] getInvestmentDetailsReport(Long society_investment_id);
}
