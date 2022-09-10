package com.techvg.vks.loan.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.model.ViewLoanDetailsDto;

@Mapper(componentModel = "spring")
public interface ViewLoanDetailsMapper {

//	@Mapping(ignore=true,target="nomineeDtoSet")
ViewLoanDetailsDto loanDetailsToViewLoanDetailsDto(LoanDetails loanDetail);
	
	
	@AfterMapping
	default void getLoanDetails( @MappingTarget  ViewLoanDetailsDto viewLoanDetailsDto, LoanDetails loanDetails ) {
		viewLoanDetailsDto.setName(loanDetails.getMember().getLastName()+" "+loanDetails.getMember().getFirstName()+" "+loanDetails.getMember().getMiddleName());
	    viewLoanDetailsDto.setId(loanDetails.getId());
	    viewLoanDetailsDto.setMemberId(loanDetails.getMember().getId());

		if(loanDetails.getLoanProduct() !=null) {
			viewLoanDetailsDto.setProductName(loanDetails.getLoanProduct().getProductName());
	    	viewLoanDetailsDto.setLoanProductId(loanDetails.getLoanProduct().getId());
	    	viewLoanDetailsDto.setMaxAmount(loanDetails.getLoanProduct().getMaxLoanAmount());
			viewLoanDetailsDto.setDuration(loanDetails.getLoanProduct().getDuration());
			viewLoanDetailsDto.setInterestRate(loanDetails.getLoanProduct().getInterestRate());
	    	viewLoanDetailsDto.setResolutionNo(loanDetails.getLoanProduct().getResolutionNo());
	    	viewLoanDetailsDto.setResolutionDate(loanDetails.getLoanProduct().getResolutionDate());
		}
		if(loanDetails.getLoanDemand().getCrop()!=null)
		{
			viewLoanDetailsDto.setCropName(loanDetails.getLoanDemand().getCrop().getCropName());
		}
    	
    	loanDetails.getMember().getLand().forEach(action->{
    		if(loanDetails.getId().equals(action.getMLLoanNo())) {
    		viewLoanDetailsDto.setLandAreaR(action.getLandAreaR());
    		viewLoanDetailsDto.setLandAreaHector(action.getLandAreaHector());
    		viewLoanDetailsDto.setLandGatno(action.getLandGatno());
    		}
    	});
		
	}
	

}
