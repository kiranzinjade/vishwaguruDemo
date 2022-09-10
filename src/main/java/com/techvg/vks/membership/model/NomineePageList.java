package com.techvg.vks.membership.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class NomineePageList extends PageImpl<NomineeDto> {
	
	public NomineePageList(List<NomineeDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public NomineePageList(List<NomineeDto> content) {
        super(content);
    }



}
