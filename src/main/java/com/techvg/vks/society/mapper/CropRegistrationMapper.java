package com.techvg.vks.society.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.techvg.vks.society.domain.CropRegistration;
import com.techvg.vks.society.model.CropRegistrationDto;
@Mapper(componentModel = "spring")

public interface CropRegistrationMapper {
	
	CropRegistrationDto cropRegistrationToCropRegistrationDto(CropRegistration cropRegistration);
	CropRegistration cropRegistrationDtoToCropRegistration(CropRegistrationDto cropRegistrationDto);
	 List<CropRegistrationDto>domainToDtoList(List<CropRegistration> domainList);
	 
}
