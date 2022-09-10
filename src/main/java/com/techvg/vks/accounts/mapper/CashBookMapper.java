package com.techvg.vks.accounts.mapper;

import com.techvg.vks.accounts.domain.CashBook;
import com.techvg.vks.accounts.model.CashBookDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CashBookMapper {

    CashBook toDomain(CashBookDto dto);
    CashBookDto toDto(CashBook domain);
}
