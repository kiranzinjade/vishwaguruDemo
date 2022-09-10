package com.techvg.vks.loan.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class CropLoanDetailsPageList extends PageImpl<CropLoanDetailsDto> {
    public CropLoanDetailsPageList(List<CropLoanDetailsDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public CropLoanDetailsPageList(List<CropLoanDetailsDto> content) {
        super(content);
    }


}
