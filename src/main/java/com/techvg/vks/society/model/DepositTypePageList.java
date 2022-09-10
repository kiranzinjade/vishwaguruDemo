package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class DepositTypePageList extends PageImpl<DepositTypeDto>{

	public DepositTypePageList(List<DepositTypeDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public DepositTypePageList(List<DepositTypeDto> content) {
        super(content);
    }
}
