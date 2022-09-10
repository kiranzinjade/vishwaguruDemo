package com.techvg.vks.loan.mapper;

import com.techvg.vks.loan.domain.NPAData;
import com.techvg.vks.loan.model.NPADataDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NPAMapper {

    NPAData toDomain(NPADataDto dto);
    NPADataDto toDto(NPAData domain);
}
