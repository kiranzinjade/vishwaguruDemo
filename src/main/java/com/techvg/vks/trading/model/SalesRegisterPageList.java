package com.techvg.vks.trading.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class SalesRegisterPageList extends PageImpl<SalesRegisterDto>{
	public SalesRegisterPageList(List<SalesRegisterDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public SalesRegisterPageList(List<SalesRegisterDto> content) {
        super(content);
    }
}
