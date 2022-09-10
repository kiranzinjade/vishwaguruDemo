package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class SocietyPageList extends PageImpl<SocietyDto> {
	
	public SocietyPageList(List<SocietyDto> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public SocietyPageList(List<SocietyDto> content) {
		super(content);
	}

}