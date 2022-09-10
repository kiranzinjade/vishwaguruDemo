package com.techvg.vks.loan.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


public class ShortTermSubsidyPageList extends PageImpl<ShortTermSubsidyDto> {
	
    public ShortTermSubsidyPageList(List<ShortTermSubsidyDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public ShortTermSubsidyPageList(List<ShortTermSubsidyDto> content) {
        super(content);
    }

}
