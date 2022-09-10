package com.techvg.vks.loan.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class LoanInterestPageList extends PageImpl<LoanInterestDto> {
	public LoanInterestPageList(List<LoanInterestDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public LoanInterestPageList(List<LoanInterestDto> content) {
        super(content);
    }


}