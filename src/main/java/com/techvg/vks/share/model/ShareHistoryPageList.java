package com.techvg.vks.share.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class ShareHistoryPageList extends PageImpl<ShareHistoryDto> {
	public ShareHistoryPageList(List<ShareHistoryDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public ShareHistoryPageList(List<ShareHistoryDto> content) {
        super(content);
    }
}