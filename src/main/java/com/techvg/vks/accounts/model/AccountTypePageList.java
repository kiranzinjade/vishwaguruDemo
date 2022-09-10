package com.techvg.vks.accounts.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class AccountTypePageList extends PageImpl<AccountTypeDto> {

    public AccountTypePageList(List<AccountTypeDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public AccountTypePageList(List<AccountTypeDto> content) {
        super(content);
    }
}
