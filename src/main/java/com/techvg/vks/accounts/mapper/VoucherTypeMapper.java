package com.techvg.vks.accounts.mapper;

import com.techvg.vks.accounts.domain.VoucherType;
import com.techvg.vks.accounts.model.VoucherTypeDto;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoucherTypeMapper {
    VoucherType toDomain(VoucherTypeDto dto);
    VoucherTypeDto toDto(VoucherType domain);
    List<VoucherTypeDto> domainToDtoList(List<VoucherType> domainList);
}
