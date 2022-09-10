package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class AgmPageList extends PageImpl<AgmDto> {

	public AgmPageList(List<AgmDto> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public AgmPageList(List<AgmDto> content) {
		super(content);
	}

}
