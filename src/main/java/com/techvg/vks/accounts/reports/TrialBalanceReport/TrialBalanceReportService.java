package com.techvg.vks.accounts.reports.TrialBalanceReport;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techvg.vks.accounts.model.GeneralLedgerDto;

@Service

public interface TrialBalanceReportService {

	List<GeneralLedgerDto> getAssetAndExpenseList(Integer transMonth);

	List<GeneralLedgerDto> getLiabilityAndIncomeList(Integer transMonth);

}
