package com.techvg.vks.accounts.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class JournalPageList extends PageImpl<JournalDto> {
    public JournalPageList(List<JournalDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public JournalPageList(List<JournalDto> content) {
        super(content);
    }
}
