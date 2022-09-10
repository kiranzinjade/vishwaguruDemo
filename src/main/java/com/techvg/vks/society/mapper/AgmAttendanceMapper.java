package com.techvg.vks.society.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.techvg.vks.society.domain.AgmAttendance;
import com.techvg.vks.society.model.AgmAttendanceDto;

@Mapper(componentModel = "spring")
public interface AgmAttendanceMapper {

	AgmAttendanceDto agmAttendanceToAgmAttendanceDto(AgmAttendance agmAttendance);

	@InheritInverseConfiguration

	AgmAttendance agmAttendanceDtoToAgmAttendance(AgmAttendanceDto agmAttendanceDto);

}
