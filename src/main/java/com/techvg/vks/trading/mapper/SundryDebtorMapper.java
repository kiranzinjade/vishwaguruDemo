package com.techvg.vks.trading.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.techvg.vks.trading.domain.SundryDebtor;
import com.techvg.vks.trading.model.SundryDebtorDto;

@Mapper(componentModel = "spring")

public interface SundryDebtorMapper {
	
    @Mapping( source = "sundryDebtor.sundryDebtorTransaction", target = "sundryDebtorTransaction")
    @Mapping( source = "sundryDebtor.member.id", target = "memberId")


	SundryDebtorDto sundryDebtorToSundryDebtorDto(SundryDebtor sundryDebtor);
	SundryDebtor sundryDebtorDtoToSundryDebtor(SundryDebtorDto sundryDebtorDto);
	
	
	@AfterMapping
	default void getDebtorMemberDetails(@MappingTarget SundryDebtorDto sundryDebtorDto,SundryDebtor sundryDebtor) {
		sundryDebtorDto.setFullName(sundryDebtor.getMember().getFirstName()+" "+sundryDebtor.getMember().getMiddleName()+" "+sundryDebtor.getMember().getLastName());
		
	}
}
