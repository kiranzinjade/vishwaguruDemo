package com.techvg.vks.society.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.techvg.vks.society.domain.Society;
import com.techvg.vks.society.model.SocietyDto;

@Mapper(componentModel = "spring")
public interface SocietyMapper {

	@Mapping(source = "fileData",target = "imgFile")
	SocietyDto societyToSocietyDto(Society society);
	Society societyDtoToSociety(SocietyDto societyDto);
	
    List<SocietyDto> domainToDtoList(List<Society> domainList);

}