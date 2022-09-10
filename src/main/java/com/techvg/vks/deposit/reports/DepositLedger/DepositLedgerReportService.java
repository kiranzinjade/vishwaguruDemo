package com.techvg.vks.deposit.reports.DepositLedger;

import org.springframework.stereotype.Service;

@Service
public interface DepositLedgerReportService {

	byte[] getDepositLedgerReport(Long id,Long accountNo);

}
