package com.techvg.vks.membership.mapper;

import com.techvg.vks.membership.domain.MemCropReg;
import com.techvg.vks.membership.model.MemCropRegDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemCropRegMapper {

    MemCropReg toDomain(MemCropRegDto dto);

    MemCropRegDto toDto(MemCropReg domain);
    List<MemCropRegDto> toDtoList(List<MemCropReg> domainList);

    @AfterMapping
    default void updateDetails(MemCropReg domain , @MappingTarget MemCropRegDto dto ) {
        dto.setMemberName(domain.getMember().getLastName()+" "+domain.getMember().getFirstName()+" "+domain.getMember().getMiddleName());
        dto.setCropName(domain.getCrop().getCropName());
        dto.setCropId(domain.getCrop().getId());
        dto.setMemberId(domain.getMember().getId());
    }
}
