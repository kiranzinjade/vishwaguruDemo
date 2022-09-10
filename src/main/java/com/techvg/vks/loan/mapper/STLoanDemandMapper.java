package com.techvg.vks.loan.mapper;

import com.techvg.vks.loan.domain.LoanDemand;
import com.techvg.vks.loan.model.LoanDemandDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface STLoanDemandMapper {


	LoanDemandDto loanDemandToLoanDemandDto(LoanDemand loanDemand);
	
	LoanDemand loanDemandDtoToLoanDemand(LoanDemandDto loanDemandDto);

	List<LoanDemandDto> toDtoList(List<LoanDemand> domainList);
	
	@AfterMapping
	default void getShotTermLoanDetails(LoanDemand loanDemand ,@MappingTarget LoanDemandDto loanDemandDto ) {
		loanDemandDto.setMemberFullName(loanDemand.getMember().getLastName()+" "+loanDemand.getMember().getFirstName()+" "+loanDemand.getMember().getMiddleName());
		loanDemandDto.setCropName(loanDemand.getCrop().getCropName());
		loanDemandDto.setCropRegistrationId(loanDemand.getCrop().getId());
		loanDemandDto.setMemberId(loanDemand.getMember().getId());
		loanDemandDto.setCropLoanDemandId(loanDemand.getCropLoanDemand().getId());
		loanDemandDto.setCropLandAreaHector(loanDemand.getCropLandAreaHector());
		loanDemandDto.setCropLandAreaR(loanDemand.getCropLandAreaR());
		loanDemandDto.setLandGatNo(loanDemand.getLand().getLandGatno());
//		loanDemand.getMember().getLand().forEach(action->{
//			loanDemandDto.setCropLandAreaHector(action.getLandAreaHector());
//			loanDemandDto.setCropLandAreaR(action.getLandAreaR());
//		});
		
	}
	
}
