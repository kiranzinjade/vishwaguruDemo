package com.techvg.vks.membership.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;



public class MemberPageList extends PageImpl<MemberDto>{
	
	public MemberPageList(List<MemberDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public MemberPageList(List<MemberDto> content) {
        super(content);
    }


}
