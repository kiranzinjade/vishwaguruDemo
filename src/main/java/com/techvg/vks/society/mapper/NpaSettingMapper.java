package com.techvg.vks.society.mapper;

import com.techvg.vks.society.domain.NpaSetting;
import com.techvg.vks.society.model.NpaSettingDto;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NpaSettingMapper {

	NpaSettingDto npaSettingToNpaSettingDto(NpaSetting npaSetting);
	NpaSetting npaSettingDtoToNpaSetting(NpaSettingDto npaSettingDto);
	
	List<NpaSettingDto> domainToDtoList(List<NpaSetting> domainList); 
}
