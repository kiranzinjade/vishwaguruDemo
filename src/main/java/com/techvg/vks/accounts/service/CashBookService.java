package com.techvg.vks.accounts.service;

import com.techvg.vks.accounts.domain.VoucherTrans;
import com.techvg.vks.accounts.model.CashBookDto;
import com.techvg.vks.accounts.model.CashBookPageList;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CashBookService {

    boolean addCashbookEntry(List<VoucherTrans> voucherTransList);
    boolean addCashbookEntry(CashBookDto cashBookDto);
    CashBookDto updateCashbookEntry(Long cashbookId, CashBookDto cashBookDto, Authentication authentication);
    CashBookDto getCashbookEntryById(Long cashbookId);
    CashBookDto deleteCashbookEntryById(Long cashbookId);
    CashBookPageList listCashbookEntries(Pageable pageable);
	CashBookDto creditAmount(CashBookDto cashBookDto, Authentication authentication);
	CashBookDto debitAmount(CashBookDto cashBookDto, Authentication authentication);
}
