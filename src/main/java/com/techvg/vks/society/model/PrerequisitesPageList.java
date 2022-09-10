package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;



public class PrerequisitesPageList extends PageImpl<PrerequisitesDto>{

	public PrerequisitesPageList(List<PrerequisitesDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public PrerequisitesPageList(List<PrerequisitesDto> content) {
        super(content);
    }
}
