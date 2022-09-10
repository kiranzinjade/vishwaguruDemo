package com.techvg.vks.society.model;

import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class AssetsPageList extends PageImpl<AssetsDto>{
	
	public AssetsPageList(List<AssetsDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public AssetsPageList(List<AssetsDto> content) {
        super(content);
    }

}
