package com.techvg.vks.loan.reports.LoanDisbursementRegister;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.techvg.vks.loan.domain.LoanDetails;

@Mapper(componentModel="spring")
public interface LoanDisbursementRegisterMapper {

	@IterableMapping(qualifiedByName="all")
	List<LoanDisbursementRegisterWrapper> mapAllDisbursementDataList(List<LoanDetails> loanDetail);
	

	@Named("all")
	@Mappings({

			@Mapping(ignore = true, target = "name"), @Mapping(ignore = true, target = "address"),

	})
	LoanDisbursementRegisterWrapper mapAllFdData(LoanDetails loanDetails);

	@AfterMapping
	default void fillInProperties(final LoanDetails loanDetails,
			@MappingTarget final LoanDisbursementRegisterWrapper wrapper) {
		wrapper.setName(loanDetails.getMember().getFirstName() + " " + loanDetails.getMember().getMiddleName() + " "
				+ loanDetails.getMember().getLastName());
		loanDetails.getMember().getHouse().forEach(action -> {
			if (action.getAddressType().contentEquals("PERMANENT")) {
				wrapper.setAddress(action.getAddressLine1() + ", " + action.getAddressLine2() + ", " + action.getCity()
						+ ", " + action.getState() + ", PIN - " + action.getPincode());
			}
		});
	}


}
