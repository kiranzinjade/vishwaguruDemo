package com.techvg.vks.trading.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class SalesReturnPageList extends PageImpl<SalesReturnDto> {
    public SalesReturnPageList(List<SalesReturnDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public SalesReturnPageList(List<SalesReturnDto> content) {
        super(content);
    }
}
