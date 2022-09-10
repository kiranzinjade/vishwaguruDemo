package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class SocietyConfigurationPageList extends PageImpl<SocietyConfigurationDto> {
	public SocietyConfigurationPageList(List<SocietyConfigurationDto> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public SocietyConfigurationPageList(List<SocietyConfigurationDto> content) {
		super(content);
	}
}
