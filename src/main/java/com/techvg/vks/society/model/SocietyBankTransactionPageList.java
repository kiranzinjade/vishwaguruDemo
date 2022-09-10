package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class SocietyBankTransactionPageList extends PageImpl<SocietyBankTransactionDto>{
	
	public SocietyBankTransactionPageList(List<SocietyBankTransactionDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public SocietyBankTransactionPageList(List<SocietyBankTransactionDto> content) {
        super(content);
    }

}
