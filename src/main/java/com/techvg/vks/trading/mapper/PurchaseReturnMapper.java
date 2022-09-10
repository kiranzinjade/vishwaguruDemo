package com.techvg.vks.trading.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.techvg.vks.trading.domain.PurchaseReturn;
import com.techvg.vks.trading.model.PurchaseReturnDto;

@Mapper(componentModel = "spring")
public interface PurchaseReturnMapper {

	PurchaseReturnDto toDto(PurchaseReturn domain);
	PurchaseReturn toDomain(PurchaseReturnDto dto);
	List<PurchaseReturnDto> toDtoList(List<PurchaseReturn> purchaseReturn);
}
