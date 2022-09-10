package com.techvg.vks.accounts.reports.TrialBalanceReport;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import com.techvg.vks.accounts.domain.GeneralLedger;

@Mapper(componentModel="spring")
public interface TrialBalanceReportMapper {


	@IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
	List<TrialBalanceReportWrapper> mapAllTrialBalanceList(List<GeneralLedger> list2);
	public TrialBalanceReportWrapper mapAllTrialBalance(GeneralLedger generalLedger);
	
}

