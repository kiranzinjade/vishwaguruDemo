package com.techvg.vks.society.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.techvg.vks.society.domain.MeasuringUnit;
import com.techvg.vks.society.model.MeasuringUnitDto;

@Mapper(componentModel = "spring")
public interface MeasuringUnitMapper {

    MeasuringUnit toDomain(MeasuringUnitDto dto);
    MeasuringUnitDto toDto(MeasuringUnit domain);
    
    List<MeasuringUnitDto> domainToDtoList(List<MeasuringUnit> domainList);
}
