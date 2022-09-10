package com.techvg.vks.loan.mapper;

import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.model.LoanDetailsDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = { DisbursementMapper.class,RepaymentMapper.class},componentModel = "spring")
public interface LoanDetailsMapper {
	
	@Mapping(source="loanDetails.disbursements" ,target="disbursementDtoSet")
	@Mapping(source="loanDetails.repayment" ,target="repaymentDtoSet")
	@Mapping(source="loanDetails.member.id" ,target="memberId")
	@Mapping(source="loanDetails.member.firstName" ,target="firstName")
	@Mapping(source="loanDetails.member.middleName" ,target="middleName")
	@Mapping(source="loanDetails.member.lastName" ,target="lastName")
	@Mapping(source="loanDetails.loanDemand.id", target="loanDemandId")
	@Mapping(source="loanDetails.loanProduct.id", target="loanProductId")
	LoanDetailsDto loanDetailsToLoanDetailsDto(LoanDetails loanDetails);
	
	@InheritInverseConfiguration
    LoanDetails   LoanDetailsDtoToLoanDetails(LoanDetailsDto loanDetailsDto);
    List<LoanDetailsDto> domainToDtoList(List<LoanDetails> domainList);

}
