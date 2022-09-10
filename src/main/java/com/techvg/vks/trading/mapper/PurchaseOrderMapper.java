package com.techvg.vks.trading.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.techvg.vks.trading.domain.PurchaseOrder;
import com.techvg.vks.trading.domain.PurchaseRegister;
import com.techvg.vks.trading.domain.StockRegister;
import com.techvg.vks.trading.domain.SundryCreditor;
import com.techvg.vks.trading.model.PurchaseOrderDto;
import com.techvg.vks.trading.model.SundryCreditorDto;

@Mapper(uses = {PurchaseRegisterMapper.class},componentModel = "spring")
public interface PurchaseOrderMapper {

	//@Mapping(target="vendorRegister.id", source="dto.vendorId")
    PurchaseOrder toDomain(PurchaseOrderDto dto);
	@Mapping(target="vendorId", source="domain.vendorRegister.id")
    PurchaseOrderDto toDto(PurchaseOrder domain);
    List<PurchaseOrderDto> toDtoList(List<PurchaseOrder> purchaseOrder);
    
    	@Mapping(target="vendor.id", source="dto.vendorId")
       SundryCreditor toSundryCreditorDomain(PurchaseOrderDto dto);
       SundryCreditorDto toDomainToSundryCreditorDto(SundryCreditor sundryCreditor);

	@AfterMapping
	default void getMemberDetails(@MappingTarget PurchaseOrderDto dto,PurchaseOrder domain) {
		dto.setOwnerName(domain.getVendorRegister().getOwnerName());
		dto.setCompanyName(domain.getVendorRegister().getCompanyName());
	
	
}
	
}
