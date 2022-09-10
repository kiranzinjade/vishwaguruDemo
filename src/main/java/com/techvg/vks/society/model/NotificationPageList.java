package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class NotificationPageList extends PageImpl<NotificationDto>{
	
	public NotificationPageList(List<NotificationDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public NotificationPageList(List<NotificationDto> content) {
        super(content);
    }
}
