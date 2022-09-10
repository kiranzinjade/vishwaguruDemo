package com.techvg.vks.accounts.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class VoucherTypePageList extends PageImpl<VoucherTypeDto> {
    public VoucherTypePageList(List<VoucherTypeDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public VoucherTypePageList(List<VoucherTypeDto> content) {
        super(content);
    }
}
