package com.techvg.vks.trading.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.techvg.vks.trading.domain.SundryCreditor;
import com.techvg.vks.trading.model.SundryCreditorDto;

@Mapper(componentModel = "spring")
public interface SundryCreditorMapper {
	
    @Mapping( source = "sundryCreditor.sundryCreditorTransaction", target = "sundryCreditorTransaction")

    @Mapping( source = "sundryCreditor.vendor.id", target = "vendorId")
    @Mapping( source = "sundryCreditor.vendor.ownerName", target = "ownerName")
	SundryCreditorDto sundryCreditorToSundryCreditorDto(SundryCreditor sundryCreditor);
	SundryCreditor sundryCreditorDtoToSundryCreditor(SundryCreditorDto sundryCreditorDto);

}
