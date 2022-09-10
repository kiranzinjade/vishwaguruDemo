package com.techvg.vks.trading.mapper;

import com.techvg.vks.trading.domain.StockRegister;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.techvg.vks.trading.domain.PurchaseRegister;
import com.techvg.vks.trading.model.PurchaseRegisterDto;

@Mapper(componentModel = "spring")
public interface PurchaseRegisterMapper {
	
	@Mapping(target = "productId", source="domain.product.id")
	@Mapping(target="name", source="domain.product.name")
	@Mapping(target="purchaseOrderId", source="domain.purchaseOrder.id")
	PurchaseRegisterDto toDto(PurchaseRegister domain);
	PurchaseRegister toDomain(PurchaseRegisterDto dto);

	@Mapping(target = "quantity", source="domain.quantity")
	@Mapping(target = "expiryDate", source="domain.expiryDate")
	@Mapping(target = "batchNo", source="domain.batchNo")
	@Mapping(target = "product", source="domain.product")
	@Mapping(target = "transactionDate", source="domain.purchaseOrder.purchaseDate")
	StockRegister toStockRegisterDomain(PurchaseRegister domain);
}