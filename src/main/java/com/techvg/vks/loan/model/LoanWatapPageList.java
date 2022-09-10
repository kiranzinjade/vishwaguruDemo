package com.techvg.vks.loan.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class LoanWatapPageList extends PageImpl<LoanWatapDto> {
	
	public LoanWatapPageList(List<LoanWatapDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
	
	 public LoanWatapPageList(List<LoanWatapDto> content) {
	        super(content);
	    }
}
