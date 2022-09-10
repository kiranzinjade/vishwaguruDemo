package com.techvg.vks.loan.mapper;

import com.techvg.vks.loan.domain.KMPSociety;
import com.techvg.vks.loan.model.KMPSocietyDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KMPSocietyMapper {

    KMPSocietyDto toDto(KMPSociety domain);
    KMPSociety toDomain(KMPSocietyDto dto);
}
