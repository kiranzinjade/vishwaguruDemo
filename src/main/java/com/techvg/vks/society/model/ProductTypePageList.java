package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class ProductTypePageList extends PageImpl<ProductTypeDto> {

	public ProductTypePageList(List<ProductTypeDto> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public ProductTypePageList(List<ProductTypeDto> content) {
		super(content);
	}
 

}
