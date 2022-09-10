package com.techvg.vks.loan.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class LongLoanDemandPageList extends PageImpl<MidLongLoanDemandDto> {
	
	public LongLoanDemandPageList(List<MidLongLoanDemandDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
	
	 public LongLoanDemandPageList(List<MidLongLoanDemandDto> content) {
	        super(content);
	    }


}
