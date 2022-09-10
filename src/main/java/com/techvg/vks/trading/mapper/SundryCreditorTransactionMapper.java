package com.techvg.vks.trading.mapper;

import org.mapstruct.Mapper;

import com.techvg.vks.trading.domain.SundryCreditorTransaction;
import com.techvg.vks.trading.model.SundryCreditorTransactionDto;

@Mapper(componentModel = "spring")

public interface SundryCreditorTransactionMapper {

	 SundryCreditorTransaction toDomain(SundryCreditorTransactionDto dto);
	 SundryCreditorTransactionDto toDto(SundryCreditorTransaction sundryCreditorTransaction);
}
