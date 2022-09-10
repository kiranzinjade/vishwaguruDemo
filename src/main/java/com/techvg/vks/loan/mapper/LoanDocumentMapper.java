package com.techvg.vks.loan.mapper;

import org.mapstruct.Mapper;

import com.techvg.vks.loan.domain.LoanDocument;
import com.techvg.vks.loan.model.LoanDocumentDto;

@Mapper(componentModel = "spring")
public interface LoanDocumentMapper {
	
	LoanDocumentDto reapymentToRepaymentDto(LoanDocument  loanDocument);
	LoanDocument repaymentDtoToRepayment(LoanDocumentDto loanDocumentDto);
	
}



