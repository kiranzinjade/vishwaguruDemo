package com.techvg.vks.loan.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class MidLoanDemandPageList extends PageImpl<MidLongLoanDemandDto> {
	
	
	public MidLoanDemandPageList(List<MidLongLoanDemandDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
	
	 public MidLoanDemandPageList(List<MidLongLoanDemandDto> content) {
	        super(content);
	    }

}
