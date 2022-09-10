package com.techvg.vks.loan.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class ViewLoanDetailsPageList extends PageImpl<ViewLoanDetailsDto> {
    public ViewLoanDetailsPageList(List<ViewLoanDetailsDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public ViewLoanDetailsPageList(List<ViewLoanDetailsDto> content) {
        super(content);
    }


}
