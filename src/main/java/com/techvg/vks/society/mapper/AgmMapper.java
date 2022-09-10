package com.techvg.vks.society.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.techvg.vks.society.domain.Agm;
import com.techvg.vks.society.model.AgmDto;

@Mapper(uses = { AgmAttendanceMapper.class }, componentModel = "spring")
public interface AgmMapper {

	@Mapping(source = "agm.agmAttendance", target = "agmAttendanceDtoSet")
	AgmDto agmToAgmDto(Agm agm);

	@InheritInverseConfiguration
	Agm agmDtoToAgm(AgmDto agmDto);

}