package com.techvg.vks.deposit.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


public class DepositPageList extends PageImpl<DepositDto>  {
	public DepositPageList(List<DepositDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public DepositPageList(List<DepositDto> content) {
        super(content);
    }
}
