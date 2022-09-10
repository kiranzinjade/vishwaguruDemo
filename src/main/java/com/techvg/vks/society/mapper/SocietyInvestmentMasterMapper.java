package com.techvg.vks.society.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.techvg.vks.society.domain.SocietyInvestmentMaster;
import com.techvg.vks.society.model.SocietyInvestmentMasterDto;

@Mapper( componentModel = "spring")
public interface SocietyInvestmentMasterMapper {

	@Mapping(source="societyInvestmentMaster.societyBank.id", target="bankId")
	@Mapping(source="societyInvestmentMaster.societyBank.bankName", target="bankName")
	@Mapping(source="societyInvestmentMaster.societyBank.branchName", target="branchName")
	@Mapping(source="societyInvestmentMaster.societyBank.accountNumber", target="accountNumber")
	@Mapping(source="societyInvestmentMaster.depositType.id",target="depositTypeId")
	@Mapping(source="societyInvestmentMaster.depositType.accountType",target="accountType")
	SocietyInvestmentMasterDto societyInvestmentMasterToSocietyInvestmentMasterDto(SocietyInvestmentMaster societyInvestmentMaster);
	
	SocietyInvestmentMaster societyInvestmentMasterDtoToSocietyInvestmentMaster(SocietyInvestmentMasterDto societyInvestmentMasterDto);

	List<SocietyInvestmentMasterDto> domainToDtoList(List<SocietyInvestmentMaster> domainList);

}
