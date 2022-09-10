package com.techvg.vks.deposit.reports.fdreport;

import org.springframework.stereotype.Service;

@Service
public interface FdReportService {

	byte[] getFdlistReport();
	byte[] getFdlistReport(Long depositId);

}
