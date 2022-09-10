package com.techvg.vks.society.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.techvg.vks.society.domain.Parameter;
import com.techvg.vks.society.domain.SocietyBank;
import com.techvg.vks.society.model.ParameterDto;
import com.techvg.vks.society.model.SocietyBankDto;

@Mapper(componentModel = "spring")

public interface ParameterMapper {
	
	ParameterDto parameterToParameterDto(Parameter parameter);
	Parameter parameterDtoToParameter(ParameterDto parameterDto);
	 List<ParameterDto> domainToDtoList(List<Parameter> domainList);
}
