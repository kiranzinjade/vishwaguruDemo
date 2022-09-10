package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class SocietyInvestmentPageList extends PageImpl<SocietyInvestmentDto> {

	public SocietyInvestmentPageList(List<SocietyInvestmentDto> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public SocietyInvestmentPageList(List<SocietyInvestmentDto> content) {
		super(content);
	}

}
