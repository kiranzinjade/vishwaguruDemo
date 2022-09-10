package com.techvg.vks.loan.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.techvg.vks.loan.domain.Amortization;
import com.techvg.vks.loan.model.AmortizationDto;

@Mapper(componentModel = "spring")
public interface AmortizationMapper {
	
	AmortizationDto amortizationToAmortizationDto(Amortization amortization);
	Amortization amortizationToDtoAmortization(AmortizationDto amortizationDto);
	 List<AmortizationDto> domainToDtoList(List<Amortization> domainList);
}
