package com.techvg.vks.loan.mapper;

import com.techvg.vks.loan.domain.KMPCrop;
import com.techvg.vks.loan.model.KMPCropDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KMPCropMapper {

    KMPCrop toDomain(KMPCropDto dto);
    KMPCropDto toDto(KMPCrop domain);
    List<KMPCropDto> toDtoList(List<KMPCrop> domainList);
}
