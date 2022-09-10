package com.techvg.vks.society.mapper;

import org.mapstruct.Mapper;

import com.techvg.vks.society.domain.InterestShortTermLoan;
import com.techvg.vks.society.model.InterestShortTermLoanDto;

@Mapper(componentModel = "spring")
public interface InterestShortTermLoanMapper {
	InterestShortTermLoanDto interestShortTermLoanToInterestShortTermLoanDto(InterestShortTermLoan interestShortTermLoan);
	InterestShortTermLoan interestShortTermLoangDtoToInterestShortTermLoan(InterestShortTermLoanDto interestShortTermLoanDto);
	   
}