package com.techvg.vks.society.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.techvg.vks.society.model.PrerequisitesDto;
import com.techvg.vks.society.model.PrerequisitesPageList;

public interface PrerequisitesService {

	PrerequisitesDto addPrerequisite(PrerequisitesDto prerequisitesDto, Authentication authentication);

	PrerequisitesPageList listPrerequisites(Pageable pageable);
	PrerequisitesDto deletePrerequisiteById(Long id);

	PrerequisitesDto updatePrereqisite(Long id, PrerequisitesDto prerequisitesDto);

	PrerequisitesDto getPrerequisiteById(Long id);
}
