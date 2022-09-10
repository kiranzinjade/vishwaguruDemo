package com.techvg.vks.accounts.mapper;

import com.techvg.vks.accounts.domain.AccountMapping;
import com.techvg.vks.accounts.model.AccountMappingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMappingMapper {

    AccountMapping toDomain(AccountMappingDto dto);

    @Mapping(source = "domain.ledgerAccount.id", target = "ledgerAccId")
    AccountMappingDto toDto(AccountMapping domain);

    List<AccountMappingDto> toDtoList(List<AccountMapping> domainList);
}
