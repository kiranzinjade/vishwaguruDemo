package com.techvg.vks.membership.model;

import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class MemberBelongingPageList extends PageImpl<MemberBelongingDto> {
	public MemberBelongingPageList(List<MemberBelongingDto> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public MemberBelongingPageList(List<MemberBelongingDto> content) {
		super(content);
	}

}