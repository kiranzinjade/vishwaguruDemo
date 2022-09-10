package com.techvg.vks.accounts.mapper;

import com.techvg.vks.accounts.domain.AccountType;
import com.techvg.vks.accounts.model.AccountTypeDto;
import com.techvg.vks.society.domain.MeasuringUnit;
import com.techvg.vks.society.model.MeasuringUnitDto;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountTypeMapper {

    AccountType toAccountType(AccountTypeDto accountTypeDto);
    AccountTypeDto toAccountTypeDto(AccountType accountType);
    
    List<AccountTypeDto> domainToDtoList(List<AccountType> domainList);
}
