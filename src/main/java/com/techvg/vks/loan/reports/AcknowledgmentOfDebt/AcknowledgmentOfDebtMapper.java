package com.techvg.vks.loan.reports.AcknowledgmentOfDebt;

import com.techvg.vks.loan.domain.Amortization;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel="spring")
public interface AcknowledgmentOfDebtMapper {
	@IterableMapping(qualifiedByName="all")
  	List<AcknowledgmentOfDebtWrapper> mapAllAcknowledgmentOfDebtDataList(List<Amortization> amortizationlist);

	@Named("all")
	
	@Mappings({
		@Mapping(ignore=true,target="member_id"),
		@Mapping(ignore=true,target="nameBorrower"),
		@Mapping(ignore=true,target="loan_details_id"),
		@Mapping(ignore=true,target="typeOfLoan"),	
		@Mapping(ignore=true,target="outStanding"),	


	})

	AcknowledgmentOfDebtWrapper mapAllAcknowledgmentOfDebtData(Amortization amortization);
	
	

@AfterMapping
default void fillInProperties(final Amortization amortization,@MappingTarget final AcknowledgmentOfDebtWrapper wrapper ) {
  
  wrapper.setMember_id(amortization.loanDetails.member.getId());
  wrapper.setNameBorrower(amortization.loanDetails.member.getFirstName()+" "+amortization.loanDetails.member.getFatherName()+" "+amortization.loanDetails.member.getLastName());
  wrapper.setLoan_details_id(amortization.loanDetails.getId());
  wrapper.setTypeOfLoan(amortization.loanDetails.getLoanType());
  wrapper.setOutStanding(amortization.getOutstandingPrinciple());
	
	
}





}
