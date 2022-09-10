package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class CropRegistrationPageList extends PageImpl<CropRegistrationDto>{
	
	public CropRegistrationPageList(List<CropRegistrationDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public CropRegistrationPageList(List<CropRegistrationDto> content) {
        super(content);
    }

}
