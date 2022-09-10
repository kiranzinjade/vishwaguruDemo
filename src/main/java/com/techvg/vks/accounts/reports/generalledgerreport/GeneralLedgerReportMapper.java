package com.techvg.vks.accounts.reports.generalledgerreport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.techvg.vks.accounts.domain.GeneralLedger;

@Mapper(componentModel="spring")

public interface GeneralLedgerReportMapper {

//	public HashMap<Long,ArrayList<GeneralLedgerReportWrapper>> mapAllLedgerList(HashMap<Long,ArrayList<GeneralLedger>> ledgerList);
	public List<GeneralLedgerReportWrapper> mapAllList(List<GeneralLedger> list);
	public GeneralLedgerReportWrapper mapAllLedger(GeneralLedger generalLedger);
	
	@AfterMapping
	default void fillInProperties(final GeneralLedger generalLedger, @MappingTarget final GeneralLedgerReportWrapper wrapper ) {
		wrapper.setAccountHeadCode(generalLedger.ledgerAccounts.getAccHeadCode());
		wrapper.setTransDate(generalLedger.getTransDate());
		wrapper.setCreditAmt(generalLedger.getCreditAmt());
		wrapper.setDebitAmt(generalLedger.getDebitAmt());
		wrapper.setBalance(generalLedger.getBalance());
		wrapper.setRemark(generalLedger.getRemark());
		wrapper.setTransType(generalLedger.getTransType());
		wrapper.setLedgerAccountId(generalLedger.getLedgerAccounts().getId());
	}
}
