package com.techvg.vks.society.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class NpaSettingPageList extends PageImpl<NpaSettingDto>{
	
	public NpaSettingPageList(List<NpaSettingDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public NpaSettingPageList(List<NpaSettingDto> content) {
        super(content);
    }

}
