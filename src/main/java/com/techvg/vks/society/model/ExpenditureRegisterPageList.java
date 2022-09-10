package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class ExpenditureRegisterPageList extends PageImpl<ExpenditureRegisterDto>  {
	 public ExpenditureRegisterPageList(List<ExpenditureRegisterDto> content, Pageable pageable, long total) {
	        super(content, pageable, total);
	    }

	    public ExpenditureRegisterPageList(List<ExpenditureRegisterDto> content) {
	        super(content);
	    }

}
