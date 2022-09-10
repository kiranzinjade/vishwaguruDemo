package com.techvg.vks.accounts.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class LedgerAccountsPageList extends PageImpl<LedgerAccountsDto> {
    public LedgerAccountsPageList(List<LedgerAccountsDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public LedgerAccountsPageList(List<LedgerAccountsDto> content) {
        super(content);
    }
}
