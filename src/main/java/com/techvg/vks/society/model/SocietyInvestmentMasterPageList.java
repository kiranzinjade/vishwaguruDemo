package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class SocietyInvestmentMasterPageList extends PageImpl<SocietyInvestmentMasterDto> {

	public SocietyInvestmentMasterPageList(List<SocietyInvestmentMasterDto> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public SocietyInvestmentMasterPageList(List<SocietyInvestmentMasterDto> content) {
		super(content);
	}

}
