package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class InterestShortTermLoanPageList extends PageImpl<InterestShortTermLoanDto> {
	public InterestShortTermLoanPageList(List<InterestShortTermLoanDto> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public InterestShortTermLoanPageList(List<InterestShortTermLoanDto> content) {
		super(content);
	}

}
