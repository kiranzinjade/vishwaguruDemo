package com.techvg.vks.share.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import java.util.List;

public class SharesPageList extends PageImpl<SharesDto> {
	public SharesPageList(List<SharesDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public SharesPageList(List<SharesDto> content) {
        super(content);
    }
}

