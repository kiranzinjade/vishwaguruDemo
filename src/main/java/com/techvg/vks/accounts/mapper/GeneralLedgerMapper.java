package com.techvg.vks.accounts.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.techvg.vks.accounts.domain.GeneralLedger;
import com.techvg.vks.accounts.model.GeneralLedgerDto;

@Mapper(componentModel = "spring")
public interface GeneralLedgerMapper {
	
    GeneralLedger toDomain(GeneralLedgerDto dto);
    @Mapping(source="domain.ledgerAccounts.id", target="ledgerAccountId")
    @Mapping(source="domain.ledgerAccounts.accHeadCode", target="accHeadCode")
    GeneralLedgerDto toDto(GeneralLedger domain);
    List<GeneralLedgerDto> domainToDtoList(List<GeneralLedger> domainList);
}
