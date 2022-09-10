package com.techvg.vks.loan.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class ListingPage_PageList extends PageImpl<ListingPageDto> {
    public ListingPage_PageList(List<ListingPageDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public ListingPage_PageList(List<ListingPageDto> content) {
        super(content);
    }


}
