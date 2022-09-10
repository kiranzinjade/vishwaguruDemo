package com.techvg.vks.loan.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class KMPMemberPageList extends PageImpl<KMPMemberDto> {
	
	
	public KMPMemberPageList(List<KMPMemberDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
	
	 public KMPMemberPageList(List<KMPMemberDto> content) {
	        super(content);
	    }

}
