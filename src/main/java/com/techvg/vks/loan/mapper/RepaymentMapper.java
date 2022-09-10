package com.techvg.vks.loan.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.techvg.vks.loan.domain.Repayment;
import com.techvg.vks.loan.model.RepaymentDto;

@Mapper(componentModel = "spring")
public interface RepaymentMapper {
	

	RepaymentDto reapymentToRepaymentDto(Repayment  repaymnet);
	Repayment repaymentDtoToRepayment(RepaymentDto repaymentDto);
	  List<RepaymentDto> toDtoList(List<Repayment> domainList);
	
}
