package com.techvg.vks.society.mapper;

import com.techvg.vks.society.domain.SocietyMeetingDetails;
import com.techvg.vks.society.model.SocietyMeetDetailsDto;
import com.techvg.vks.society.model.SocietyMeetingDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SocietyMeetingDetailsMapper {

    SocietyMeetingDetails toDomain(SocietyMeetingDetailsDto dto);

    @Mapping(source = "domain.societyMeeting.id", target = "societyMeetingId")
    SocietyMeetingDetailsDto toDetailDto(SocietyMeetingDetails domain);
    SocietyMeetDetailsDto toDto(SocietyMeetingDetails domain);


    List<SocietyMeetingDetailsDto> toDetailDtoList(List<SocietyMeetingDetails> domainList);
    List<SocietyMeetDetailsDto> toDtoList(List<SocietyMeetingDetails> domainList);

}
