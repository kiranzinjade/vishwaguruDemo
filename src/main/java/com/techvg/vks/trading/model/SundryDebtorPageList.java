package com.techvg.vks.trading.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class SundryDebtorPageList extends PageImpl<SundryDebtorDto>{
	
	public SundryDebtorPageList(List<SundryDebtorDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public SundryDebtorPageList(List<SundryDebtorDto> content) {
        super(content);
    }
}



