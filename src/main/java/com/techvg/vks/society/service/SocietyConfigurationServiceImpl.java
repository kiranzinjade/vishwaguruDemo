package com.techvg.vks.society.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.SocietyConfigHistory;
import com.techvg.vks.society.domain.SocietyConfiguration;
import com.techvg.vks.society.mapper.SocietyConfigHistoryMapper;
import com.techvg.vks.society.mapper.SocietyConfigurationMapper;
import com.techvg.vks.society.model.SocietyConfigHistoryDto;
import com.techvg.vks.society.model.SocietyConfigHistoryPageList;
import com.techvg.vks.society.model.SocietyConfigurationDto;
import com.techvg.vks.society.model.SocietyConfigurationPageList;
import com.techvg.vks.society.repository.SocietyConfigHistoryRepository;
import com.techvg.vks.society.repository.SocietyConfigurationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocietyConfigurationServiceImpl implements SocietyConfigurationService {
	private final SocietyConfigurationRepository societyConfigurationRepository;
	private final SocietyConfigurationMapper societyConfigurationMapper;
	private final SocietyConfigHistoryRepository societyConfigHistoryRepository;
	private final SocietyConfigHistoryMapper societyConfigHistoryMapper;

	public SocietyConfigurationDto setAuthorisedCapital(SocietyConfigurationDto societyConfigurationDto,
			Authentication authentication) {
		Optional<SocietyConfiguration> societyConfigurationObjOptional = societyConfigurationRepository
				.findByConfigName((societyConfigurationDto.getConfigName()));
		if (societyConfigurationObjOptional.isPresent()) {
			SocietyConfiguration societyConfiguration = societyConfigurationObjOptional.get();
			SocietyConfigHistory societyConfigHistory = new SocietyConfigHistory();
			societyConfigHistory.setConfigName(societyConfiguration.getConfigName());
			societyConfigHistory.setConfigType(societyConfiguration.getConfigType());
			societyConfigHistory.setValue(societyConfiguration.getValue());
			societyConfigHistory.setYear(societyConfiguration.getYear());
			societyConfigHistory.setStatus(societyConfiguration.getStatus());
			societyConfigHistory.setIsDeleted(false);

			societyConfigHistoryRepository.save(societyConfigHistory);

			SocietyConfiguration societyConfiguration1 = societyConfigurationMapper
					.societyConfigurationDtoToSocietyConfiguration(societyConfigurationDto);
			societyConfiguration1.setId(societyConfiguration.getId());
			societyConfiguration1 = societyConfigurationRepository.save(societyConfiguration1);
			return societyConfigurationMapper.societyConfigurationToSocietyConfigurationDto(societyConfiguration1);
		} else {
			SocietyConfiguration societyConfiguration = societyConfigurationMapper
					.societyConfigurationDtoToSocietyConfiguration(societyConfigurationDto);
			societyConfiguration = societyConfigurationRepository.save(societyConfiguration);
			return societyConfigurationMapper.societyConfigurationToSocietyConfigurationDto(societyConfiguration);
		}
	}

	@Override
	public List<SocietyConfigHistoryDto> getAuthorisedCapitalByType(String configType) {
		log.debug("REST request to get Revision history of type : {}", configType);
		return societyConfigHistoryMapper
				.toSocietyConfigHistoryDto(societyConfigHistoryRepository.findByConfigType(configType));
	}

	@Override
	@Cacheable(cacheNames = "socListCache")
	public List<SocietyConfigurationDto> getAll() {
		return societyConfigurationMapper.toDtoList(societyConfigurationRepository.findAll());
	}

	public SocietyConfigHistoryPageList listRevisonHitroyForType(Pageable pageable) {
		Page<SocietyConfigHistory> societyConfigHistoryPage;
		societyConfigHistoryPage = societyConfigHistoryRepository.findByStatus("A", pageable);
		return new SocietyConfigHistoryPageList(
				societyConfigHistoryPage.getContent().stream()
						.map(societyConfigHistoryMapper::societyConfigHistoryToSocietyConfigHistoryDto)
						.collect(Collectors.toList()),
				PageRequest.of(societyConfigHistoryPage.getPageable().getPageNumber(),
						societyConfigHistoryPage.getPageable().getPageSize()),
				societyConfigHistoryPage.getTotalElements());

	}

	public SocietyConfiguration getConfiguration(String configName){
		return societyConfigurationRepository
				.findByConfigName(configName).orElseThrow(NotFoundException::new);
	}

	@Override
	public double getConfigurationValue(String configName) {
		SocietyConfiguration societyConfiguration = societyConfigurationRepository
				.findByConfigName(configName).orElseThrow(NotFoundException::new);
		return societyConfiguration.getValue();
	}

	@Override
	public boolean updateConfigValue(String configName, double value) {
		SocietyConfiguration societyConfiguration = societyConfigurationRepository
				.findByConfigName(configName).orElseThrow(NotFoundException::new);
		societyConfiguration.setValue(value);
		societyConfigurationRepository.saveAndFlush(societyConfiguration);
		return true;
	}

	@Override
	public SocietyConfigurationDto updateSocietyConfig(Long id, SocietyConfigurationDto societyConfigurationDto) {
		
			SocietyConfiguration config = societyConfigurationRepository.findById(id).orElseThrow(NotFoundException::new);
			societyConfigurationDto.setId(config.getId());
			SocietyConfiguration config1 = societyConfigurationMapper.societyConfigurationDtoToSocietyConfiguration(societyConfigurationDto);
			SocietyConfigHistory societyConfigHistory = new SocietyConfigHistory();
			societyConfigHistory.setConfigName(config1.getConfigName());
			societyConfigHistory.setConfigType(config1.getConfigType());
			societyConfigHistory.setValue(config1.getValue());
			societyConfigHistory.setYear(config1.getYear());
			societyConfigHistory.setStatus(config1.getStatus());
			societyConfigHistory.setIsDeleted(false);
			societyConfigHistoryRepository.save(societyConfigHistory);
			return societyConfigurationMapper.societyConfigurationToSocietyConfigurationDto(societyConfigurationRepository.save(config1));
		}

	@Override
	public SocietyConfigurationPageList listConfigForType(Pageable pageable) {
		Page<SocietyConfiguration> societyConfigurationPage;
		societyConfigurationPage = societyConfigurationRepository.findByStatus("A", pageable);
		return new SocietyConfigurationPageList(
				societyConfigurationPage.getContent().stream()
						.map(societyConfigurationMapper::societyConfigurationToSocietyConfigurationDto)
						.collect(Collectors.toList()),
				PageRequest.of(societyConfigurationPage.getPageable().getPageNumber(),
						societyConfigurationPage.getPageable().getPageSize()),
				societyConfigurationPage.getTotalElements());
	}

	@Override
	public SocietyConfigurationDto deleteConfigById(Long id) {
		SocietyConfiguration societyConfiguration = societyConfigurationRepository.findById(id).orElseThrow(NotFoundException::new);
			if (societyConfiguration != null) {
				societyConfiguration.setIsDeleted(true);
				societyConfigurationRepository.save(societyConfiguration);
			}
		return societyConfigurationMapper.societyConfigurationToSocietyConfigurationDto(societyConfiguration);
	
	}

	@Override
	public SocietyConfigurationPageList listByConfigType(String configType, Pageable pageable) {
		Page<SocietyConfiguration> societyConfigurationPage;
		List<SocietyConfiguration> list = societyConfigurationRepository.findByConfigType(configType);
		societyConfigurationPage =  new PageImpl<>(list,pageable,list.size());
	       
		return new SocietyConfigurationPageList(societyConfigurationPage
										.getContent()
										.stream()
										.map(societyConfigurationMapper::societyConfigurationToSocietyConfigurationDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(societyConfigurationPage.getPageable().getPageNumber(),
													societyConfigurationPage.getPageable().getPageSize()),
											societyConfigurationPage.getTotalElements());
	}
	

}
