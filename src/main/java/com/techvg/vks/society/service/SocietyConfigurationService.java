package com.techvg.vks.society.service;

import java.util.List;

import com.techvg.vks.society.domain.SocietyConfiguration;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.techvg.vks.society.model.SocietyConfigHistoryDto;
import com.techvg.vks.society.model.SocietyConfigHistoryPageList;
import com.techvg.vks.society.model.SocietyConfigurationDto;
import com.techvg.vks.society.model.SocietyConfigurationPageList;

@Service
public interface SocietyConfigurationService {

	SocietyConfigurationDto setAuthorisedCapital(SocietyConfigurationDto societyConfigurationDto,
			Authentication authentication);

	SocietyConfigHistoryPageList listRevisonHitroyForType(Pageable pageable);

	List<SocietyConfigHistoryDto> getAuthorisedCapitalByType(String configType);

	List<SocietyConfigurationDto> getAll();

	SocietyConfiguration getConfiguration(String configName);

	double getConfigurationValue(String configName);

	boolean updateConfigValue(String configName, double value);

	SocietyConfigurationDto updateSocietyConfig(Long id, SocietyConfigurationDto societyConfigurationDto);

	SocietyConfigurationPageList listConfigForType(Pageable pageable);

	SocietyConfigurationDto deleteConfigById(Long id);

	SocietyConfigurationPageList listByConfigType(String configType, Pageable pageable);

	}
