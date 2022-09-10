package com.techvg.vks.accounts.mapper;

import com.techvg.vks.accounts.domain.AccountType;
import com.techvg.vks.accounts.domain.LedgerAccounts;
import com.techvg.vks.accounts.model.AccountTypeDto;
import com.techvg.vks.accounts.model.LedgerAccountsDto;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface LedgerAccountMapper {

    @Mapping( target = "parentLedger", ignore = true )
    LedgerAccounts toLedgerAccounts(LedgerAccountsDto ledgerAccountsDto);

    @Mapping( target = "parentLedger", ignore = true )
    LedgerAccountsDto toLedgerAccountsDto(LedgerAccounts ledgerAccounts);
    
    List<LedgerAccountsDto> domainToDtoList(List<LedgerAccounts> domainList);

    @AfterMapping
    default void addBackReference(@MappingTarget LedgerAccountsDto target, LedgerAccounts ledgerAccounts) {

        LedgerAccountsDto parent = new LedgerAccountsDto();

        if(ledgerAccounts.getParentLedger() !=null) {

            parent.setId(ledgerAccounts.getParentLedger().getId());
            parent.setAccountName(ledgerAccounts.getParentLedger().getAccountName());
            target.setParentLedger(parent);
            target.setParentAccHead(ledgerAccounts.getParentLedger().getAccHeadCode());
            target.setParentId(ledgerAccounts.getParentLedger().getId());
        }
        target.setAccTypeName(ledgerAccounts.getAccountType().getName());
        target.setAccTypeId(ledgerAccounts.getAccountType().getId());

    }
}
