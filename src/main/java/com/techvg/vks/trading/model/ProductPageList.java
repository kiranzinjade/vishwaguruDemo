package com.techvg.vks.trading.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ProductPageList extends PageImpl<ProductDto> {

    public ProductPageList(List<ProductDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public ProductPageList(List<ProductDto> content) {
        super(content);
    }
}

