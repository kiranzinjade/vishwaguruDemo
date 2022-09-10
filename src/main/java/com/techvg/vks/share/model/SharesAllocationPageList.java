package com.techvg.vks.share.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class SharesAllocationPageList extends PageImpl<SharesAllocationDto> {
	public SharesAllocationPageList(List<SharesAllocationDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public SharesAllocationPageList(List<SharesAllocationDto> content) {
        super(content);
    }
}
