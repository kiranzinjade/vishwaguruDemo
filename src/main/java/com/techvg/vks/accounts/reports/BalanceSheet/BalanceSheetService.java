package com.techvg.vks.accounts.reports.BalanceSheet;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techvg.vks.accounts.domain.LedgerAccounts;
import com.techvg.vks.accounts.model.LedgerAccountsDto;

@Service
public interface BalanceSheetService {

	byte[] generateBalanceSheet();

	List<LedgerAccountsDto> listLiabilities();

	List<LedgerAccountsDto> listAssets();

}
