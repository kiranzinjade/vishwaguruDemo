package com.techvg.vks.society.mapper;

import com.techvg.vks.society.domain.SocietyBank;
import com.techvg.vks.society.model.SocietyBankDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SocietyBankMapper {

	//@Mapping(source="depositType.id",target="depositTypeId")
	//@Mapping(source="depositType.accountType",target="accountType")

    SocietyBankDto bankToBankDto(SocietyBank bank);
    SocietyBank bankDtoToBank(SocietyBankDto bankDto);
    List<SocietyBankDto> domainToDtoList(List<SocietyBank> domainList);

}
