package com.techvg.vks.trading.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.techvg.vks.trading.domain.SundryDebtorTransaction;
import com.techvg.vks.trading.model.SundryDebtorTransactionDto;

@Mapper(componentModel = "spring")

public interface SundryDebtorTransactionMapper {

//    @Mapping( source = "sundryDebtorTransaction.sundryDebtor", target = "debtorId")

	 SundryDebtorTransaction toDomain(SundryDebtorTransactionDto dto);
	 List<SundryDebtorTransactionDto> toDto(List<SundryDebtorTransaction> list);
}
