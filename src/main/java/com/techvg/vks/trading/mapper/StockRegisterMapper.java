package com.techvg.vks.trading.mapper;

import com.techvg.vks.trading.domain.StockRegister;
import com.techvg.vks.trading.model.StockRegisterDto;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockRegisterMapper {
	StockRegister toDomain(StockRegisterDto dto);

	@Mapping( source = "domain.product.id", target = "productId")
	@Mapping( source = "domain.product.name", target = "productName")
	StockRegisterDto toDto(StockRegister domain);

	List<StockRegisterDto> toDto(List<StockRegister> findListByProductId);
}
