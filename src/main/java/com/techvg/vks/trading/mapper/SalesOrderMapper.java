package com.techvg.vks.trading.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.techvg.vks.trading.domain.SalesOrder;
import com.techvg.vks.trading.domain.SundryDebtor;
import com.techvg.vks.trading.model.SalesOrderDto;
import com.techvg.vks.trading.model.SundryDebtorDto;

@Mapper(uses = {SalesRegisterMapper.class}, componentModel = "spring")
public interface SalesOrderMapper {

   
	SalesOrder toDomain(SalesOrderDto dto);
	@Mapping(target="memberId", source="domain.member.id")
	SalesOrderDto toDto(SalesOrder domain);
	List<SalesOrderDto> toDtoList(List<SalesOrder> salesOrder); 
	
	@AfterMapping
	default void getMemberDetails(@MappingTarget SalesOrderDto dto,SalesOrder domain) {
		dto.setFullName(domain.getMember().getFirstName()+" "+domain.getMember().getMiddleName()+" "+domain.getMember().getLastName());
	
	
}
	@Mapping(target="member.id", source="salesOrderDto.memberId")
	SundryDebtor toSundryDebtorDomain(SalesOrderDto salesOrderDto);
	SundryDebtorDto toDomainToSundryDebtorDto(SundryDebtor sundryDebtor);
	
}
