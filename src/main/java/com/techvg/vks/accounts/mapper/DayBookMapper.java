package com.techvg.vks.accounts.mapper;

import com.techvg.vks.accounts.domain.DayBook;
import com.techvg.vks.accounts.domain.GeneralLedger;
import com.techvg.vks.accounts.model.DayBookDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DayBookMapper {

    DayBook toDomain(DayBookDto dto);
    DayBookDto toDto(DayBook domain);

    List<DayBookDto> toDtoList(List<DayBook> dtoList);

    @Mapping(source = "particulars", target = "remark")
    @Mapping(source = "transDate", target = "transDate")
    @Mapping(source = "transType", target = "transType")
    @Mapping(source = "debitTotalAmt", target = "debitAmt")
    @Mapping(source = "creditTotalAmt", target = "creditAmt")
    //@Mapping(source = "balance", target = "balance")
    GeneralLedger toGeneralLedger(DayBook domain);
}
