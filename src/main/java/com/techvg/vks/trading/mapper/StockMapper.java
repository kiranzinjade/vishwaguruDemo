package com.techvg.vks.trading.mapper;

import com.techvg.vks.trading.domain.Stock;
import com.techvg.vks.trading.model.StockDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StockMapper {
	
	StockDto toDto(Stock domain);
	Stock toDomain(StockDto dto);

}
