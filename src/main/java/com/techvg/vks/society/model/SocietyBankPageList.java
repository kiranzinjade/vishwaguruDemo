package com.techvg.vks.society.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class SocietyBankPageList extends PageImpl<SocietyBankDto> {
    public SocietyBankPageList(List<SocietyBankDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public SocietyBankPageList(List<SocietyBankDto> content) {
        super(content);
    }
}
