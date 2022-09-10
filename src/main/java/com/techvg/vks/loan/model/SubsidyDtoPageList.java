package com.techvg.vks.loan.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class SubsidyDtoPageList extends PageImpl<SubsidyDto> {
	
    public SubsidyDtoPageList(List<SubsidyDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public SubsidyDtoPageList(List<SubsidyDto> content) {
        super(content);
    }


}
