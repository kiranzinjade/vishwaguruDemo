package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class ParameterPageList extends PageImpl<ParameterDto>{

	public ParameterPageList(List<ParameterDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public ParameterPageList(List<ParameterDto> content) {
        super(content);
    }
}
