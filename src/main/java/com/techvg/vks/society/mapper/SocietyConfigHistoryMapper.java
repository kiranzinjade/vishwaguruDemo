package com.techvg.vks.society.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.techvg.vks.society.domain.SocietyConfigHistory;
import com.techvg.vks.society.domain.SocietyConfiguration;
import com.techvg.vks.society.model.SocietyConfigHistoryDto;

@Mapper(componentModel = "spring")
public interface SocietyConfigHistoryMapper {

	
	SocietyConfigHistoryDto societyConfigHistoryToSocietyConfigHistoryDto(SocietyConfigHistory societyConfigHistory);
	SocietyConfigHistory societyConfigHistoryDtoToSocietyConfigHistory(SocietyConfigHistoryDto societyConfigHistoryDto);
	SocietyConfigHistory socConfigToSocConfigHistory(SocietyConfiguration conf);
	SocietyConfiguration socConfigHisToSocConfig(SocietyConfigHistory conf);
	List<SocietyConfigHistoryDto> toSocietyConfigHistoryDto(List<SocietyConfigHistory> societyConfigHistory);
	SocietyConfigHistory toHistory(SocietyConfiguration ccn);
}


