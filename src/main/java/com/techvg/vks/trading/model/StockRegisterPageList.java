package com.techvg.vks.trading.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class StockRegisterPageList extends PageImpl<StockRegisterDto>{
	public StockRegisterPageList(List<StockRegisterDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public StockRegisterPageList(List<StockRegisterDto> content) {
        super(content);
    }
}
