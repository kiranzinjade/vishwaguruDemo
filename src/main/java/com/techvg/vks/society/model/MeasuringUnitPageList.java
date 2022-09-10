package com.techvg.vks.society.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class MeasuringUnitPageList extends PageImpl<MeasuringUnitDto> {

    public MeasuringUnitPageList(List<MeasuringUnitDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public MeasuringUnitPageList(List<MeasuringUnitDto> content) {
        super(content);
    }
}