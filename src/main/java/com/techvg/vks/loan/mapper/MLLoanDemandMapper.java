package com.techvg.vks.loan.mapper;

import com.techvg.vks.loan.model.LoanDemandDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.techvg.vks.loan.domain.LoanDemand;

import com.techvg.vks.loan.model.MidLongLoanDemandDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MLLoanDemandMapper {
	
    MidLongLoanDemandDto loanDemandToMidLongLoanDemandDto(LoanDemand loanDemand);
	
	LoanDemand midLongloanDemandDtoToLoanDemand(MidLongLoanDemandDto midLongloanDemandDto);

	List<MidLongLoanDemandDto> toDtoList(List<LoanDemand> domainList);
	
	@AfterMapping
	default void getMidLongTermLoanDetails(LoanDemand loanDemand , @MappingTarget  MidLongLoanDemandDto loanDemandDto ) {
		loanDemandDto.setMemberFullName(loanDemand.getMember().getLastName()+" "+loanDemand.getMember().getFirstName()+" "+loanDemand.getMember().getMiddleName());
		loanDemandDto.setMemberId(loanDemand.getMember().getId());
		if(loanDemand.getLoanProduct() !=null) {
		loanDemandDto.setProductName(loanDemand.getLoanProduct().getProductName());
		loanDemandDto.setProductId(loanDemand.getLoanProduct().getId());
		}
	}

}
