package com.techvg.vks.membership.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class LandDocumentPageList extends PageImpl<LandDocumentDto> {
	public LandDocumentPageList(List<LandDocumentDto> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public LandDocumentPageList(List<LandDocumentDto> content) {
		super(content);
	}
	
}