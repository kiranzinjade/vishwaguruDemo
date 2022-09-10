package com.techvg.vks.trading.mapper;

import com.techvg.vks.society.domain.MeasuringUnit;
import com.techvg.vks.society.model.MeasuringUnitDto;
import com.techvg.vks.trading.domain.VendorRegister;
import com.techvg.vks.trading.model.VendorRegisterDto;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VendorRegisterMapper {
	
	VendorRegisterDto toDto(VendorRegister domain);

	VendorRegister toDomain(VendorRegisterDto dto);
	
	List<VendorRegisterDto> domainToDtoList(List<VendorRegister> domainList);
}
