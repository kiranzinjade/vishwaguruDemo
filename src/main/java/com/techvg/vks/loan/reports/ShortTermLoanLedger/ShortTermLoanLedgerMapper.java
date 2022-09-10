package com.techvg.vks.loan.reports.ShortTermLoanLedger;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.techvg.vks.loan.domain.Disbursement;

@Mapper(componentModel="spring")
public interface ShortTermLoanLedgerMapper {
	
	@IterableMapping(qualifiedByName="all")
    List<ShortTermLoanLedgerWrapper> mapAllShortTermLoanDataList(List<Disbursement> disbursement);

	@Named("all")
	@Mappings({ 
		
		@Mapping(ignore=true,target="name"),
		@Mapping(source="id",target="loanId"),
		@Mapping(source="disbursement.loanDetails.member.id",target="memberId"),
		@Mapping(source="disbursement.loanDetails.loanAmt",target="loanAmt"),
		@Mapping(source="disbursementDate",target="disbursementDate"),
		@Mapping(source="disbursement.loanDetails.loanProduct.interestRate",target="interestRate"),
		//@Mapping(source="disbursement.loanDetails.loanProduct.type",target="type"),
		@Mapping(source="disbursement.loanDetails.loanPlannedClosureDate",target="loanPlannedClosureDate")

	})
	ShortTermLoanLedgerWrapper mapAllShortTermLoanData(Disbursement disbursement);
	
	@AfterMapping
	default void fillInProperties(final Disbursement disbursement,
	      @MappingTarget final ShortTermLoanLedgerWrapper wrapper ) {
		wrapper.setName(disbursement.getLoanDetails().getMember().getFirstName()+" "+disbursement.getLoanDetails().getMember().getMiddleName()+" "+disbursement.getLoanDetails().getMember().getLastName());
		wrapper.setCropName(disbursement.getLoanDetails().getLoanDemand().getCrop().getCropName());
		if(disbursement.getLoanDetails().getLoanDemand().getLoanProduct() !=null) {
			wrapper.setInterestRate(disbursement.getLoanDetails().getLoanProduct().getInterestRate());
		}
		
	}

}
