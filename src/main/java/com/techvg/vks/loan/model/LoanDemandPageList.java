package com.techvg.vks.loan.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


public class LoanDemandPageList extends PageImpl<LoanDemandDto> {
	
	public LoanDemandPageList(List<LoanDemandDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
	
	 public LoanDemandPageList(List<LoanDemandDto> content) {
	        super(content);
	    }

}
