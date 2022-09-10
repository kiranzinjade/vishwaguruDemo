package com.techvg.vks.trading.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class SalesOrderPageList extends PageImpl<SalesOrderDto> {
    public SalesOrderPageList(List<SalesOrderDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public SalesOrderPageList(List<SalesOrderDto> content) {
        super(content);
    }
}
