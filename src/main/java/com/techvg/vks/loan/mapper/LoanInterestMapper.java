package com.techvg.vks.loan.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.model.LoanInterestDetails;
import com.techvg.vks.loan.model.LoanInterestDto;

@Mapper(componentModel = "spring")

public interface LoanInterestMapper {
	
	LoanInterestDto loanDetailsToLoanInterestDto(LoanDetails loanDetail);
	LoanInterestDto loanInterestDetailsToLoanInterestDto(LoanInterestDetails loanInterestDetails);

}
