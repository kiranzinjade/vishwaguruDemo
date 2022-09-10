package com.techvg.vks.society.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.techvg.vks.society.model.SocietyConfigurationDto;

@Service
public interface ShareConfigurationService {
	SocietyConfigurationDto updateTotalNumberOfShares(SocietyConfigurationDto societyConfigurationDto, Authentication authentication);

	SocietyConfigurationDto setMaximumShareLimit(SocietyConfigurationDto societyConfigurationDto,Authentication authentication);

	SocietyConfigurationDto setShareValue(SocietyConfigurationDto societyConfigurationDto,
			Authentication authentication);
	
	SocietyConfigurationDto updateConfigValue(SocietyConfigurationDto societyConfigurationDto,
			Authentication authentication);

	List<SocietyConfigurationDto> getAllByType();

	List<SocietyConfigurationDto> addConfi(List<SocietyConfigurationDto> societyConfigurationDtoList, Authentication authentication);

}
