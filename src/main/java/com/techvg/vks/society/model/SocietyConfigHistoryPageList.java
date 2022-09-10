package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class SocietyConfigHistoryPageList extends PageImpl<SocietyConfigHistoryDto> {
	public SocietyConfigHistoryPageList(List<SocietyConfigHistoryDto> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public SocietyConfigHistoryPageList(List<SocietyConfigHistoryDto> content) {
		super(content);
	}
}
