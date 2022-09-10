package com.techvg.vks.trading.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PurchaseReturnPageList extends PageImpl<PurchaseReturnDto> {
    public PurchaseReturnPageList(List<PurchaseReturnDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public PurchaseReturnPageList(List<PurchaseReturnDto> content) {
        super(content);
    }
}
