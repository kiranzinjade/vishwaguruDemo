package com.techvg.vks.society.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.techvg.vks.accounts.domain.VoucherType;
import com.techvg.vks.accounts.model.VoucherTypeDto;
import com.techvg.vks.society.domain.DepositType;
import com.techvg.vks.society.domain.SocietyBank;
import com.techvg.vks.society.model.DepositTypeDto;
import com.techvg.vks.society.model.SocietyBankDto;

@Mapper(componentModel = "spring")
public interface DepositTypeMapper {
	DepositTypeDto DepositTypeToDepositTypeDto(DepositType depositType);
	DepositType DepositTypeDtoToDepositType(DepositTypeDto depositTypeDto);

    List<DepositTypeDto> domainToDtoList(List<DepositType> domainList);

}
