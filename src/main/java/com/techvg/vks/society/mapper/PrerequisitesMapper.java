package com.techvg.vks.society.mapper;

import org.mapstruct.Mapper;


import com.techvg.vks.society.domain.Prerequisites;
import com.techvg.vks.society.model.PrerequisitesDto;

@Mapper(componentModel = "spring")
public interface PrerequisitesMapper {
	PrerequisitesDto prerequisitesToPrerequisitesDto(Prerequisites prerequisites);
	Prerequisites prerequisitesDtoToPrerequisites(PrerequisitesDto prerequisitesDto);

}
