package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class AssetsRegistrationPageList  extends PageImpl<AssetsRegistrationDto>{
	
	public AssetsRegistrationPageList(List<AssetsRegistrationDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public AssetsRegistrationPageList(List<AssetsRegistrationDto> content) {
        super(content);
    }

}