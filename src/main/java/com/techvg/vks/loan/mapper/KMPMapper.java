package com.techvg.vks.loan.mapper;

import com.techvg.vks.loan.domain.KMP;
import com.techvg.vks.loan.model.KMPDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KMPMapper {

    KMP toDomain(KMPDto dto);
    KMPDto toDto(KMP domain);
    List<KMPDto> toDtoList(List<KMP> domainList);
}
