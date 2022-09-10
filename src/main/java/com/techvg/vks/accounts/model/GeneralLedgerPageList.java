package com.techvg.vks.accounts.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class GeneralLedgerPageList extends PageImpl<GeneralLedgerDto> {
    public GeneralLedgerPageList(List<GeneralLedgerDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public GeneralLedgerPageList(List<GeneralLedgerDto> content) {
        super(content);
    }
}
