package com.techvg.vks.society.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.techvg.vks.config.ShareConstants;
import com.techvg.vks.society.domain.SocietyConfigHistory;
import com.techvg.vks.society.domain.SocietyConfiguration;
import com.techvg.vks.society.mapper.SocietyConfigHistoryMapper;
import com.techvg.vks.society.mapper.SocietyConfigurationMapper;
import com.techvg.vks.society.model.SocietyConfigurationDto;
import com.techvg.vks.society.repository.SocietyConfigHistoryRepository;
import com.techvg.vks.society.repository.SocietyConfigurationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShareConfigurationServiceImpl implements ShareConfigurationService {

	private final SocietyConfigurationRepository societyConfigurationRepository;
	private final SocietyConfigurationMapper societyConfigurationMapper;
	private final SocietyConfigHistoryRepository societyConfigHistoryRepository;
	private final SocietyConfigHistoryMapper societyConfigHistoryMapper;
	

		public SocietyConfigurationDto updateTotalNumberOfShares(SocietyConfigurationDto societyConfigurationDto, Authentication authentication) {

			Optional<SocietyConfiguration> societyConfigurationObjOptional = societyConfigurationRepository.findByConfigName((societyConfigurationDto.getConfigName()));

			List<SocietyConfiguration> societyConfiguration2 = societyConfigurationRepository.findByConfigType(ShareConstants.SHARES_CONFIG_TYPE);
			final Map<String, Double> sharesConfigurationMap = new HashMap<>();
			
			societyConfiguration2.forEach(singleConfiguration->{
				sharesConfigurationMap.put(singleConfiguration.getConfigName(),singleConfiguration.getValue());
			});

			Double totNumberOfShares = sharesConfigurationMap.get(ShareConstants.SHARE_CAPITAL)/sharesConfigurationMap.get(ShareConstants.SINGLE_SHARE_PRICE);
			
			if (societyConfigurationObjOptional.isPresent()) {
				SocietyConfiguration societyConfiguration = societyConfigurationObjOptional.get();
				SocietyConfigHistory societyConfigHistory = societyConfigHistoryMapper.socConfigToSocConfigHistory(societyConfiguration);
				
				societyConfigHistoryRepository.save(societyConfigHistory);

				SocietyConfiguration societyConfiguration1 = societyConfigurationMapper.societyConfigurationDtoToSocietyConfiguration(societyConfigurationDto);
				societyConfiguration1.setId(societyConfiguration.getId());
				societyConfiguration1.setValue(totNumberOfShares);
				societyConfiguration1 = societyConfigurationRepository.save(societyConfiguration1);
				return societyConfigurationMapper.societyConfigurationToSocietyConfigurationDto(societyConfiguration1);
			} 
			else {
				SocietyConfiguration societyConfiguration = societyConfigurationMapper.societyConfigurationDtoToSocietyConfiguration(societyConfigurationDto);
				societyConfiguration.setValue(totNumberOfShares);
				societyConfiguration = societyConfigurationRepository.save(societyConfiguration);
				return societyConfigurationMapper.societyConfigurationToSocietyConfigurationDto(societyConfiguration);
			}
		}
		
		@Override
		public SocietyConfigurationDto setMaximumShareLimit(SocietyConfigurationDto societyConfigurationDto, Authentication authentication) {
			Optional<SocietyConfiguration> societyConfigurationObjOptional = societyConfigurationRepository.findByConfigName((societyConfigurationDto.getConfigName()));
			
			if (societyConfigurationObjOptional.isPresent()) {
				SocietyConfiguration societyConfiguration = societyConfigurationObjOptional.get();
			
		
				SocietyConfigHistory societyConfigHistory = societyConfigHistoryMapper.socConfigToSocConfigHistory(societyConfiguration);
					
				societyConfigHistoryRepository.save(societyConfigHistory);

				SocietyConfiguration societyConfiguration1 = societyConfigurationMapper.societyConfigurationDtoToSocietyConfiguration(societyConfigurationDto);
				societyConfiguration1.setId(societyConfiguration.getId());
				societyConfiguration1 = societyConfigurationRepository.save(societyConfiguration1);
				return societyConfigurationMapper.societyConfigurationToSocietyConfigurationDto(societyConfiguration1);
			} 
			else {
				SocietyConfiguration societyConfiguration = societyConfigurationMapper.societyConfigurationDtoToSocietyConfiguration(societyConfigurationDto);
				societyConfiguration = societyConfigurationRepository.save(societyConfiguration);
				return societyConfigurationMapper.societyConfigurationToSocietyConfigurationDto(societyConfiguration);
			}
			
		}


		@Override
		public SocietyConfigurationDto setShareValue(SocietyConfigurationDto societyConfigurationDto,
				Authentication authentication) {
			Optional<SocietyConfiguration> societyConfigurationObjOptional = societyConfigurationRepository.findByConfigName((societyConfigurationDto.getConfigName()));
			
			if (societyConfigurationObjOptional.isPresent()) {
				SocietyConfiguration societyConfiguration = societyConfigurationObjOptional.get();
				SocietyConfigHistory societyConfigHistory = societyConfigHistoryMapper.socConfigToSocConfigHistory( societyConfiguration);
				societyConfigHistory.setIsDeleted(false);

				societyConfigHistoryRepository.save(societyConfigHistory);

				SocietyConfiguration societyConfiguration1 = societyConfigurationMapper
						.societyConfigurationDtoToSocietyConfiguration(societyConfigurationDto);
				societyConfiguration1.setId(societyConfiguration.getId());
				
				societyConfiguration1 = societyConfigurationRepository.save(societyConfiguration1);
				return societyConfigurationMapper.societyConfigurationToSocietyConfigurationDto(societyConfiguration1);
			} else {
				SocietyConfiguration societyConfiguration = societyConfigurationMapper.societyConfigurationDtoToSocietyConfiguration(societyConfigurationDto);
				societyConfiguration = societyConfigurationRepository.save(societyConfiguration);
				return societyConfigurationMapper.societyConfigurationToSocietyConfigurationDto(societyConfiguration);
			}
		}

		// Loan settlement Configuration
		
		@Override
		public SocietyConfigurationDto updateConfigValue(SocietyConfigurationDto societyConfigurationDto,
				Authentication authentication) {
			Optional<SocietyConfiguration> societyConfigurationObjOptional = societyConfigurationRepository.findByConfigName((societyConfigurationDto.getConfigName()));
			
			
			if (societyConfigurationObjOptional.isPresent()) {
				SocietyConfiguration societyConfiguration = societyConfigurationObjOptional.get();
				SocietyConfigHistory societyConfigHistory =societyConfigHistoryMapper.socConfigToSocConfigHistory( societyConfiguration);
				
				societyConfigHistory.setIsDeleted(false);
	        
				societyConfigHistoryRepository.save(societyConfigHistory);

				SocietyConfiguration societyConfiguration1 = societyConfigurationMapper.societyConfigurationDtoToSocietyConfiguration(societyConfigurationDto);
				societyConfiguration1.setId(societyConfiguration.getId());
				societyConfiguration1 = societyConfigurationRepository.save(societyConfiguration1);
				return societyConfigurationMapper.societyConfigurationToSocietyConfigurationDto(societyConfiguration1);
			} else {
				SocietyConfiguration societyConfiguration = societyConfigurationMapper.societyConfigurationDtoToSocietyConfiguration(societyConfigurationDto);
				societyConfiguration = societyConfigurationRepository.save(societyConfiguration);
				return societyConfigurationMapper.societyConfigurationToSocietyConfigurationDto(societyConfiguration);
			}
		}

		@Override
		public List<SocietyConfigurationDto> getAllByType() {
			
			return  societyConfigurationMapper.toDtoList(societyConfigurationRepository.findByConfigType(ShareConstants.SHARES_CONFIG_TYPE));
		}

		@Override
		public List<SocietyConfigurationDto> addConfi(List<SocietyConfigurationDto> societyConfigurationDtoList,Authentication authentication) {
			
			List<SocietyConfiguration> list= new ArrayList<SocietyConfiguration>();
			societyConfigurationDtoList.forEach(action->{
				
				Optional<SocietyConfiguration> societyConfigurationObjOptional = societyConfigurationRepository.findByConfigName((action.getConfigName()));
				
				
					SocietyConfiguration societyConfiguration = societyConfigurationObjOptional.get();
				
			
					SocietyConfigHistory societyConfigHistory = societyConfigHistoryMapper.socConfigToSocConfigHistory(societyConfiguration);
						
					societyConfigHistoryRepository.save(societyConfigHistory);
					
					SocietyConfiguration societyConfiguration1 = societyConfigurationMapper.societyConfigurationDtoToSocietyConfiguration(action);
					societyConfiguration1.setId(societyConfiguration.getId());
					societyConfiguration1.setYear(Calendar.getInstance().get(Calendar.YEAR));
				
					societyConfiguration1 = societyConfigurationRepository.save(societyConfiguration1);
									
					list.add(societyConfiguration1);
										
				});
			
			return societyConfigurationMapper.toDtoList(list);
		}
		
		

}
