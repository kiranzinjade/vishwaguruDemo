package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class LoanProductList extends PageImpl<LoanProductDto>{

	public LoanProductList(List<LoanProductDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public LoanProductList(List<LoanProductDto> content) {
        super(content);
    }

}
