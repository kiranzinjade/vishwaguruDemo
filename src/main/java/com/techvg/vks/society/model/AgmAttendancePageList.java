package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class AgmAttendancePageList extends PageImpl<AgmAttendanceDto> {

	public AgmAttendancePageList(List<AgmAttendanceDto> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public AgmAttendancePageList(List<AgmAttendanceDto> content) {
		super(content);
	}

}
