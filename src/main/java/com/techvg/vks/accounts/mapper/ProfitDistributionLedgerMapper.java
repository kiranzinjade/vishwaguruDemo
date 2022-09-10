package com.techvg.vks.accounts.mapper;

import org.mapstruct.Mapper;

import com.techvg.vks.accounts.domain.ProfitDistributionLedger;
import com.techvg.vks.accounts.model.ProfitDistributionLedgerDto;



@Mapper(componentModel = "spring")
public interface ProfitDistributionLedgerMapper {
	

	ProfitDistributionLedgerDto profitDistributionLedgerToProfitDistributionLedgerDto(ProfitDistributionLedger profitDistributionLedger);
	ProfitDistributionLedger profitDistributionLedgerDtoToProfitDistributionLedger(ProfitDistributionLedgerDto profitDistributionLedgerDto);


}
