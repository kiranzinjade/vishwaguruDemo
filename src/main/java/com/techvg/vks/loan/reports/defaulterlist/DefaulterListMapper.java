package com.techvg.vks.loan.reports.defaulterlist;

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
public interface DefaulterListMapper {
	
	@IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
	public List<DefaulterListWrapper> mapAllDefaulterDataList(List<LoanDetails> loanDetailslist);

	@Mappings({ 

		@Mapping(ignore=true,target="name"),
		@Mapping(ignore=true,target="member_id"),
		@Mapping(ignore=true,target="id"),
		@Mapping(ignore=true, target="disbursementDate"),
		@Mapping(ignore=true, target="closingPrinciple"),
		//@Mapping(ignore=true, target="totalLoanInterest"),
		@Mapping(source="loanAmt", target="loanAmt"),
		@Mapping(source="loanPlannedClosureDate", target="loanPlannedClosureDate")
		
	})
	public DefaulterListWrapper mapAllDefaulterData(LoanDetails loanDetails);


	
	@AfterMapping
	default void fillInProperties(final LoanDetails loanDetails, @MappingTarget final DefaulterListWrapper wrapper ) {
	
		wrapper.setMember_id(loanDetails.getMember().getId());
		wrapper.setId(loanDetails.getId());
		wrapper.setName(loanDetails.getMember().getLastName()+" "+loanDetails.getMember().getMiddleName()+" "+loanDetails.getMember().getFirstName());
		
		loanDetails.getDisbursements().forEach(action->{		
			wrapper.setDisbursementDate(action.getDisbursementDate());	
		}); 
		loanDetails.getRepayment().forEach(action->{			
			wrapper.setClosingPrinciple(action.getClosingPrinciple());			
		});

		/*loanDetails.getRepayment().forEach(action->{			
			wrapper.setClosing_principle(action.getClosing_principle());			
		});*/

		wrapper.setLoanAmt(loanDetails.getLoanAmt());
		wrapper.setLoanPlannedClosureDate(loanDetails.getLoanPlannedClosureDate());
	
	}
}
