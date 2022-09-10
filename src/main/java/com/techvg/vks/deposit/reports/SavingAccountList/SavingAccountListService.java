package com.techvg.vks.deposit.reports.SavingAccountList;

import org.springframework.stereotype.Service;

@Service
public interface SavingAccountListService {

	byte[] getsavingAccountListReport();
	public byte[] getsavingAccountListReportforAll();

}
