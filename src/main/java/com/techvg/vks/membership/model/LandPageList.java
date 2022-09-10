package com.techvg.vks.membership.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;



public class LandPageList extends PageImpl<LandDto> {
    public LandPageList(List<LandDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public LandPageList(List<LandDto> content) {
        super(content);
    }
    
}