package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


public class SocietyMeetingPageList extends PageImpl<SocietyMeetingDto> {

	public SocietyMeetingPageList(List<SocietyMeetingDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public SocietyMeetingPageList(List<SocietyMeetingDto> content) {
        super(content);
    }

}
