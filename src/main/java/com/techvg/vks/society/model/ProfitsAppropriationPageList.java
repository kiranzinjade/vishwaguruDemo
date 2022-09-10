package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class ProfitsAppropriationPageList extends PageImpl<ProfitsAppropriationDto> {
	
	public ProfitsAppropriationPageList(List<ProfitsAppropriationDto> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public ProfitsAppropriationPageList(List<ProfitsAppropriationDto> content) {
		super(content);
	}

}
