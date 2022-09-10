package com.techvg.vks.society.mapper;

import com.techvg.vks.society.domain.LoanCharges;
import com.techvg.vks.society.model.LoanChargesDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanChargesMapper {
	
	LoanChargesDto loanChargesToLoanChargesDto(LoanCharges loanCharges);
	LoanCharges loanChargesDtoToLoanCharges(LoanChargesDto loanChargesDto);


}
