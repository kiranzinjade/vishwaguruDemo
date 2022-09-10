package com.techvg.vks.loan.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.techvg.vks.loan.domain.LoanDemand;
import com.techvg.vks.loan.domain.ShortTermSubsidy;
import com.techvg.vks.loan.model.LoanDemandDto;
import com.techvg.vks.loan.model.ShortTermSubsidyDto;
import com.techvg.vks.loan.model.SubsidyDto;

@Mapper(componentModel = "spring")
public interface ShortTermSubsidyMapper {
	
	@Mapping(source="shortTermSubsidy.loanDetails.id", target="loanDetailsId")
	ShortTermSubsidyDto shortTermSubsidyToShortTermSubsidyDto(ShortTermSubsidy shortTermSubsidy);
	
	ShortTermSubsidy shortTermSubsidyDtoToShortTermSubsidy(ShortTermSubsidyDto shortTermSubsidyDto);
	
	SubsidyDto shortTermSubsidyToSubsidyDto(ShortTermSubsidy shortTermSubsidy);
	
	ShortTermSubsidy SubsidyDtoToShortTermSubsidy(SubsidyDto SubsidyDto);
	
	
	@AfterMapping
	default void getShotTermLoanDetails(ShortTermSubsidy shortTermSubsidy ,@MappingTarget ShortTermSubsidyDto shortTermSubsidyDto ) {
		shortTermSubsidyDto.setFullName(shortTermSubsidy.getLoanDetails().getMember().getFirstName()+" "+shortTermSubsidy.getLoanDetails().getMember().getMiddleName()+" "+shortTermSubsidy.getLoanDetails().getMember().getLastName());
		shortTermSubsidyDto.setLoanAccountNo(shortTermSubsidy.getLoanDetails().getLoanAccountNo());
		shortTermSubsidyDto.setLoanClosureDate(shortTermSubsidy.getLoanDetails().getLoanCloserDate());
	}

}
