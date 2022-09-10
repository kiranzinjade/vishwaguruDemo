package com.techvg.vks.accounts.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class VouchersPageList extends PageImpl<VouchersDto> {
    public VouchersPageList(List<VouchersDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public VouchersPageList(List<VouchersDto> content) {
        super(content);
    }
}
