package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class ExpenditureTypePageList extends PageImpl<ExpenditureTypeDto> {

	public ExpenditureTypePageList(List<ExpenditureTypeDto> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public ExpenditureTypePageList(List<ExpenditureTypeDto> content) {
		super(content);
	}
 
}
