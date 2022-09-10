package com.techvg.vks.membership.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class MemberBankPageList extends PageImpl<MemberBankDto> {
	public MemberBankPageList(List<MemberBankDto> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public MemberBankPageList(List<MemberBankDto> content) {
		super(content);
	}
}