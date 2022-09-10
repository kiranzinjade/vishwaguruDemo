package com.techvg.vks.society.mapper;


import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.techvg.vks.society.domain.DepositAccount;
import com.techvg.vks.society.model.DepositAccountDto;

@Mapper(componentModel = "spring")
public interface DepositAccountMapper {

    DepositAccount toDomain(DepositAccountDto dto);

    @Mapping(source = "domain.depositType.id", target = "depositAccTypeId")
    @Mapping(source = "domain.depositType.accountType", target = "depositAccType")
    DepositAccountDto toDto(DepositAccount domain);

    List<DepositAccountDto> domainToDtoList(List<DepositAccount> domainList);

    
    

}
