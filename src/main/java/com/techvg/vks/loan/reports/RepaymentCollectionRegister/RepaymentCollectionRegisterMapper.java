package com.techvg.vks.loan.reports.RepaymentCollectionRegister;

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
public interface RepaymentCollectionRegisterMapper {
	@IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
	public List<RepaymentCollectionRegisterWrapper> mapAllRegisterDataList(List<LoanDetails> loanDetailslist);

	@Mappings({ 

		@Mapping(ignore=true,target="name"),
		@Mapping(ignore=true,target="member_id"),
		@Mapping(ignore=true,target="id"),
		
	})
	public RepaymentCollectionRegisterWrapper mapAllRegisterData(LoanDetails loanDetails);


	
	@AfterMapping
	default void fillInProperties(final LoanDetails loanDetails, @MappingTarget final RepaymentCollectionRegisterWrapper wrapper ) {
	
		
		wrapper.setMember_id(loanDetails.getMember().getId());
		wrapper.setId(loanDetails.getId());
		wrapper.setName(loanDetails.getMember().getLastName()+" "+loanDetails.getMember().getMiddleName()+" "+loanDetails.getMember().getFirstName());
		
		
		loanDetails.getRepayment().forEach(action->{
			System.out.println(" loan repayment details "+action);
			wrapper.setInsatllmentDate(action.getInstallmentDate());
			wrapper.setClosingPrinciple(action.getClosingPrinciple());	
			wrapper.setBalanceInterest(action.getBalanceInterest());
			wrapper.setPrinciplePaid(action.getPrinciplePaid());
			wrapper.setInterestPaid(action.getInterestPaid());
			wrapper.setInstallmentAmt(action.getInstallmentAmt());
		}); 
		
		wrapper.setLoanAmt(loanDetails.getLoanAmt());
		wrapper.setLoanPlannedClosureDate(loanDetails.getLoanPlannedClosureDate());
		wrapper.setLoanClassfication(loanDetails.getLoanClassification());
		wrapper.setLoanType(loanDetails.getLoanType());
	
	}
}
