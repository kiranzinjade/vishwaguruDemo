package com.techvg.vks.membership.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class NotificationDetailsPageList extends PageImpl<NotificationDetailsDto> {
		
		public NotificationDetailsPageList(List<NotificationDetailsDto> content, Pageable pageable, long total) {
	        super(content, pageable, total);
	    }

	    public NotificationDetailsPageList(List<NotificationDetailsDto> content) {
	        super(content);
	    }

}
