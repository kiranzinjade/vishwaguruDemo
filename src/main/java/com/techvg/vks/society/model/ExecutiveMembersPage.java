package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.techvg.vks.society.model.ExecutiveMembersDto;


public class ExecutiveMembersPage extends PageImpl<ExecutiveMembersDto> {
	public ExecutiveMembersPage(List<ExecutiveMembersDto> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public ExecutiveMembersPage(List<ExecutiveMembersDto> content) {
		super(content);
	}
}