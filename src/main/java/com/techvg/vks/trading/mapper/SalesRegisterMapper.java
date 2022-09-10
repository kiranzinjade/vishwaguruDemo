package com.techvg.vks.trading.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.techvg.vks.trading.domain.SalesRegister;
import com.techvg.vks.trading.domain.StockRegister;
import com.techvg.vks.trading.model.SalesRegisterDto;

@Mapper(componentModel = "spring")
public interface SalesRegisterMapper {

	@Mapping(target = "productId", source="domain.product.id")
	@Mapping(target="name", source="domain.product.name")
	@Mapping(target="salesOrderId",source="domain.salesOrder.id")
	SalesRegisterDto toDto(SalesRegister domain);
	SalesRegister toDomain(SalesRegisterDto dto);

	@Mapping(target = "quantity", source="domain.quantity")
	@Mapping(target = "expiryDate", source="domain.expiryDate")
	@Mapping(target = "batchNo", source="domain.batchNo")
	@Mapping(target = "transactionDate", source="domain.salesOrder.salesDate")
	 StockRegister toStockRegisterDomain(SalesRegister domain);
}
