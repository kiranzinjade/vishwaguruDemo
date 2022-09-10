package com.techvg.vks.trading.mapper;

import com.techvg.vks.trading.domain.SalesReturn;
import com.techvg.vks.trading.model.SalesReturnDto;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SalesReturnMapper {

	SalesReturnDto toDto(SalesReturn domain);
	SalesReturn toDomain(SalesReturnDto dto);
	List<SalesReturnDto> toDtoList(List<SalesReturn> salesReturn);

}
