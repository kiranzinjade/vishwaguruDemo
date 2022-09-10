package com.techvg.vks.loan.reports.ShortTermMemberList;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueMappingStrategy;

import com.techvg.vks.loan.domain.LoanDetails;

@Mapper(componentModel="spring")
public interface MemberListMapper {
	
	@IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
	public List<MemberListWrapper> mapAllDataList(List<LoanDetails> loanDetailslist);

	@Mappings({ 
		@Mapping(ignore=true,target="name"),
		@Mapping(ignore=true,target="centralGovInterest"),
		@Mapping(ignore=true,target="stateGovInterest"),
		@Mapping(ignore=true,target="distBankInterest"),
	})
	public MemberListWrapper mapAllData(LoanDetails loanDetails);
	
	@AfterMapping
	default void fillInProperties(final LoanDetails loanDetails, @MappingTarget final MemberListWrapper wrapper ) {
	
		wrapper.setMemberId(loanDetails.getMember().getId());
		wrapper.setId(loanDetails.getId());
		wrapper.setName(loanDetails.getMember().getLastName()+" "+loanDetails.getMember().getMiddleName()+" "+loanDetails.getMember().getFirstName());
		wrapper.setLoanAccountNo(loanDetails.getLoanAccountNo());
		wrapper.setLoanAmt(loanDetails.getLoanAmt());
		wrapper.setLoanCloserDate(loanDetails.getLoanCloserDate());
		wrapper.setLoanStatus(loanDetails.getLoanStatus());
		wrapper.setCentralGovInterest(loanDetails.getShortTermSubsidy().getCentralGovInterest());
		wrapper.setStateGovInterest(loanDetails.getShortTermSubsidy().getStateGovInterest());
		wrapper.setDistBankInterest(loanDetails.getShortTermSubsidy().getDistBankInterest());
		
	}

}
