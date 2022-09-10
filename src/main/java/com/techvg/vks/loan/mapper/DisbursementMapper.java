package com.techvg.vks.loan.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.techvg.vks.loan.domain.Disbursement;
import com.techvg.vks.loan.model.DisbursementDto;
import com.techvg.vks.loan.model.ViewLoanDetailsDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DisbursementMapper {
		
	DisbursementDto disbursementToDisbursementDto(Disbursement disbursement);

	Disbursement disbursementDtoToDisbursement(DisbursementDto disbursementDto);

	List<DisbursementDto> toDtoList(List<Disbursement> domainList);

	ViewLoanDetailsDto disbursementToViewLoanDetails(Disbursement disbursement );
	 
	 // methos to map disburesement date with viewLoandetails Disbursement date
	
	@AfterMapping
	default void getLoanDetails( @MappingTarget  ViewLoanDetailsDto viewLoanDetailsDto, Disbursement disbursement ) {
		viewLoanDetailsDto.setDisbursementDate(disbursement.getDisbursementDate());
	}

}
