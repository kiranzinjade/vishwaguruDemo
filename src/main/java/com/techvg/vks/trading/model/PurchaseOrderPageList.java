package com.techvg.vks.trading.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PurchaseOrderPageList extends PageImpl<PurchaseOrderDto> {
    public PurchaseOrderPageList(List<PurchaseOrderDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public PurchaseOrderPageList(List<PurchaseOrderDto> content) {
        super(content);
    }
}
