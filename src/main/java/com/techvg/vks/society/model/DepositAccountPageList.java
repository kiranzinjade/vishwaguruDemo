package com.techvg.vks.society.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class DepositAccountPageList extends PageImpl<DepositAccountDto> {
    public DepositAccountPageList(List<DepositAccountDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public DepositAccountPageList(List<DepositAccountDto> content) {
        super(content);
    }
}
