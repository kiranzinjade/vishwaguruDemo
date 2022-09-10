package com.techvg.vks.society.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.techvg.vks.society.domain.SocietyMeeting;
import com.techvg.vks.society.model.SocietyMeetingDto;


@Mapper(componentModel = "spring")
public interface SocietyMeetingMapper {
	
	//@Mapping(source="societyMeeting.societyMeetingDetails",target="meetingDetailsList")
	SocietyMeetingDto societyMeetingToSocietyMeetingDto(SocietyMeeting societyMeeting);
	
	//@InheritInverseConfiguration
	SocietyMeeting societyMeetingDtoToSocietyMeeting(SocietyMeetingDto societyMeetingDto);


}
