package com.techvg.vks.accounts.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class AccountMappingPageList extends PageImpl<AccountMappingDto>{
	
	public AccountMappingPageList(List<AccountMappingDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public AccountMappingPageList(List<AccountMappingDto> content) {
        super(content);
    }


}
