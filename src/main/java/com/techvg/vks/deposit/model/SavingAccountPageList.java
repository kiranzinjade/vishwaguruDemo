package com.techvg.vks.deposit.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


public class SavingAccountPageList extends PageImpl<SavingAccountDto> {
	
	public SavingAccountPageList(List<SavingAccountDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public SavingAccountPageList(List<SavingAccountDto> content) {
        super(content);
    }

}
