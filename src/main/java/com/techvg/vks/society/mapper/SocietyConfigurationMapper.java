package com.techvg.vks.society.mapper;

import com.techvg.vks.society.domain.SocietyConfiguration;
import com.techvg.vks.society.model.SocietyConfigurationDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface SocietyConfigurationMapper {

	SocietyConfigurationDto societyConfigurationToSocietyConfigurationDto(SocietyConfiguration societyConfiguration);

	SocietyConfiguration societyConfigurationDtoToSocietyConfiguration(SocietyConfigurationDto societyConfigurationDto);

	List<SocietyConfigurationDto> toDtoList(List<SocietyConfiguration> domainList);
	
	List<SocietyConfiguration> toDomainList(List<SocietyConfigurationDto> DtoList);//

}
