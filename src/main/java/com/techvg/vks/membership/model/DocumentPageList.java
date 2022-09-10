package com.techvg.vks.membership.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class DocumentPageList  extends PageImpl<DocumentDto>{

	public DocumentPageList(List<DocumentDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public DocumentPageList(List<DocumentDto> content) {
        super(content);
    }

}
