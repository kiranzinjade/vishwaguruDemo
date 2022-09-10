package com.techvg.vks.trading.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PurchaseRegisterPageList extends PageImpl<PurchaseRegisterDto>{
	public PurchaseRegisterPageList(List<PurchaseRegisterDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public PurchaseRegisterPageList(List<PurchaseRegisterDto> content) {
        super(content);
    }

}
