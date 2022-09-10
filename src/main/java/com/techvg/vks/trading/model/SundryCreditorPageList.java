package com.techvg.vks.trading.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class SundryCreditorPageList extends PageImpl<SundryCreditorDto>{
	
	public SundryCreditorPageList(List<SundryCreditorDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public SundryCreditorPageList(List<SundryCreditorDto> content) {
        super(content);
    }
}
