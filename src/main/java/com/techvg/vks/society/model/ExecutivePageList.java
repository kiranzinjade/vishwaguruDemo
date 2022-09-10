package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class ExecutivePageList extends PageImpl<ExecutiveMembersDto> {
	public ExecutivePageList(List<ExecutiveMembersDto> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public ExecutivePageList(List<ExecutiveMembersDto> content) {
		super(content);
	}
}
