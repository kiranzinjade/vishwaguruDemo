package com.techvg.vks.membership.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class MemCropRegPageList extends PageImpl<MemCropRegDto> {
    public MemCropRegPageList(List<MemCropRegDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public MemCropRegPageList(List<MemCropRegDto> content) {
        super(content);
    }
}
