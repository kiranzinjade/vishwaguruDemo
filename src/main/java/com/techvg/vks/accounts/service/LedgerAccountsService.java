package com.techvg.vks.accounts.service;

import com.techvg.vks.accounts.model.LedgerAccountSearchCriteria;
import com.techvg.vks.accounts.model.LedgerAccountsDto;
import com.techvg.vks.accounts.model.LedgerAccountsPageList;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LedgerAccountsService {
    LedgerAccountsDto addLedgerAccounts(LedgerAccountsDto ledgerAccountsDto);
    LedgerAccountsDto updateLedgerAccounts(Long ledgerAccountsId, LedgerAccountsDto ledgerAccountsDto, Authentication authentication);
    LedgerAccountsDto getLedgerAccountsById(Long ledgerAccountsId);
    LedgerAccountsPageList listLedgerAccounts(Pageable pageable);
    LedgerAccountsDto deleteLedgerAccountsById(Long ledgerAccountsId);
    List<LedgerAccountsDto> listAllLedgerAccount();
    List<LedgerAccountsDto> listLedgerAccountByParent(String parentAccHeadCode);

    List<LedgerAccountsDto> listSocietyExpenseAccounts();
    List<LedgerAccountsDto> listSocietyExpenseProvisionAccounts();
    List<LedgerAccountsDto> listTradingExpenseAccounts();

    List<LedgerAccountsDto> listLedgerAccounts(LedgerAccountSearchCriteria criteria);
}
