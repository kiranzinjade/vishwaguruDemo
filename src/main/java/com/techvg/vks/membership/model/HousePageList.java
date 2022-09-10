package com.techvg.vks.membership.model;

import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class HousePageList extends PageImpl<HouseDto> {
	public HousePageList(List<HouseDto> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public HousePageList(List<HouseDto> content) {
		super(content);
	}

}