package com.techvg.vks.society.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class LoanChargesPageList extends PageImpl<LoanChargesDto>{

	public LoanChargesPageList(List<LoanChargesDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public LoanChargesPageList(List<LoanChargesDto> content) {
        super(content);
    }

}
