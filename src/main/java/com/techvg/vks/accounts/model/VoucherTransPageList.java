package com.techvg.vks.accounts.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class VoucherTransPageList extends PageImpl<VoucherTransDto> {
    public VoucherTransPageList(List<VoucherTransDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public VoucherTransPageList(List<VoucherTransDto> content) {
        super(content);
    }
}
