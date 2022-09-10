package com.techvg.vks.deposit.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


public class PrintPassbookPageList extends PageImpl<PrintPassbookDto> {
	public PrintPassbookPageList(List<PrintPassbookDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public PrintPassbookPageList(List<PrintPassbookDto> content) {
        super(content);
    }


}
