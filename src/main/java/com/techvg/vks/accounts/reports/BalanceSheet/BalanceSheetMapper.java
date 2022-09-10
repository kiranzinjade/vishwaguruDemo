package com.techvg.vks.accounts.reports.BalanceSheet;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

import com.techvg.vks.accounts.domain.LedgerAccounts;


@Mapper(componentModel="spring")
public interface BalanceSheetMapper {


	@IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
	public List<BalanceSheetWrapper> mapAllLedgerList(List<LedgerAccounts> ledgerList);
	@Mapping(source ="ledger.accHeadCode", target = "accHeadCode1")
	public BalanceSheetWrapper mapAllLedger(LedgerAccounts ledger);
	
	
	
}
