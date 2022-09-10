package com.techvg.vks.accounts.reports.ProfitAndLossReport;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techvg.vks.accounts.model.LedgerAccountsDto;

@Service
public interface ProfitAndLossService {

	List<LedgerAccountsDto> listExpense();

	List<LedgerAccountsDto> listIncome();

}
