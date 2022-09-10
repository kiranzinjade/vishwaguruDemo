package com.techvg.vks.society.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.techvg.vks.society.domain.Depreciation;
import com.techvg.vks.society.model.DepreciationDto;

@Mapper(componentModel = "spring")
public interface DepreciationMapper {
	
	DepreciationDto depreciationToDepreciationDto(Depreciation depreciation);
	Depreciation depreciationDtoToDepreciation(DepreciationDto depreciationDto);

}
