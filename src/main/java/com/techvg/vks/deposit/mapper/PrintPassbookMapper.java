package com.techvg.vks.deposit.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.techvg.vks.deposit.domain.PrintPassbook;
import com.techvg.vks.deposit.model.PrintPassbookDto;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.model.ListingPageDto;

@Mapper(componentModel = "spring")
public interface PrintPassbookMapper {
		 
	PrintPassbookDto printPassbookToPrintPassbookDto(PrintPassbook printPassbook);

	PrintPassbook printPassbookDtoToPrintPassbook(PrintPassbookDto printPassbookDto);

}
