package com.techvg.vks.accounts.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class CashBookPageList extends PageImpl<CashBookDto> {
    public CashBookPageList(List<CashBookDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public CashBookPageList(List<CashBookDto> content) {
        super(content);
    }
}
