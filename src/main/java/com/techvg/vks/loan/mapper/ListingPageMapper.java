package com.techvg.vks.loan.mapper;


import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.model.ListingPageDto;
import com.techvg.vks.membership.domain.Member;

@Mapper(componentModel = "spring")
public interface ListingPageMapper {

	ListingPageDto loanDetailsToListingPageDto(LoanDetails loanDetails);
	
	@AfterMapping
	default void updatePageDto( @MappingTarget  ListingPageDto listingPageDto, LoanDetails loanDetails ) {
		listingPageDto.setName(loanDetails.getMember().getLastName()+" "+loanDetails.getMember().getFirstName()+" "+loanDetails.getMember().getMiddleName());
	}
	
}

