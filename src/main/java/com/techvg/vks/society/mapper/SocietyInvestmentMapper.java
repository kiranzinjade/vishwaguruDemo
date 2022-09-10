package com.techvg.vks.society.mapper;

import com.techvg.vks.society.domain.SocietyInvestment;
import com.techvg.vks.society.model.SocietyInvestmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SocietyInvestmentMapper {

	@Mapping(source="societyInvestment.societyInvestmentMaster.id",target="societyInvestmentId")
	@Mapping(source="societyInvestment.societyInvestmentMaster.societyBank.bankName",target="bankName")
	@Mapping(source="societyInvestment.societyInvestmentMaster.societyBank.branchName",target="branchName")
	@Mapping(source="societyInvestment.societyInvestmentMaster.societyBank.accountNumber",target="accountNumber")
	@Mapping(source="societyInvestment.societyInvestmentMaster.nomenclature",target="nomenclature")
	SocietyInvestmentDto societyInvestmentToSocietyInvestmentDto(SocietyInvestment societyInvestment);
	SocietyInvestment societyInvestmentDtoToSocietyInvestment(SocietyInvestmentDto societyInvestmentDto);
}
