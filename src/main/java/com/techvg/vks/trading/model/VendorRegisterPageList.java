package com.techvg.vks.trading.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class VendorRegisterPageList extends PageImpl<VendorRegisterDto> {
    public VendorRegisterPageList(List<VendorRegisterDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public VendorRegisterPageList(List<VendorRegisterDto> content) {
        super(content);
    }
}
