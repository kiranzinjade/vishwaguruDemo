package com.techvg.vks.loan.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.model.CropLoanDetailsDto;


@Mapper(componentModel = "spring")
public interface CropLoanDetailsMapper {

CropLoanDetailsDto loanDetailsToCropLoanDetailsDto(LoanDetails loanDetail);
	
	
	@AfterMapping
	default void getLoanDetails( @MappingTarget  CropLoanDetailsDto cropLoanDetailsDto, LoanDetails loanDetails ) {
		cropLoanDetailsDto.setName(loanDetails.getMember().getLastName()+" "+loanDetails.getMember().getFirstName()+" "+loanDetails.getMember().getMiddleName());
		cropLoanDetailsDto.setId(loanDetails.getId());
		cropLoanDetailsDto.setMemberId(loanDetails.getMember().getId());
		cropLoanDetailsDto.setMaxAllowed(loanDetails.getLoanDemand().getMaxAllowed());
		if(loanDetails.getLoanProduct() !=null) {
			cropLoanDetailsDto.setProductName(loanDetails.getLoanProduct().getProductName());
			cropLoanDetailsDto.setLoanProductId(loanDetails.getLoanProduct().getId());
			cropLoanDetailsDto.setDuration(loanDetails.getLoanProduct().getDuration());
			cropLoanDetailsDto.setInterestRate(loanDetails.getLoanProduct().getInterestRate());
//			cropLoanDetailsDto.setResolutionNo(loanDetails.getLoanProduct().getResolutionNo());
//			cropLoanDetailsDto.setResolutionDate(loanDetails.getLoanProduct().getResolutionDate());
		}
		if(loanDetails.getLoanDemand().getCrop()!=null)
		{
			cropLoanDetailsDto.setCropName(loanDetails.getLoanDemand().getCrop().getCropName());
			cropLoanDetailsDto.setSeason(loanDetails.getLoanDemand().getCrop().getSeason());
		}
    	
    	loanDetails.getMember().getLand().forEach(action->{
    		
    			cropLoanDetailsDto.setLandAreaR(action.getLandAreaR());
    			cropLoanDetailsDto.setLandAreaHector(action.getLandAreaHector());
    			cropLoanDetailsDto.setLandGatno(action.getLandGatno());
    		
    	});
		
	}
	
}
