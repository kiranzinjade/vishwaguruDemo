package com.techvg.vks.deposit.reports.DepositLedger;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;

import com.techvg.vks.deposit.domain.DepositLedger;

@Mapper(componentModel="spring")
public interface DepositLedgerReportMapper {

	@IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
	List<DepositLedgerReportWrapper> mapAllRegisterDataList(List<DepositLedger> depositLedgerList);

	DepositLedgerReportWrapper mapAllRegisterData(DepositLedger depositLedgerList);

	@AfterMapping
	default void fillInProperties(final DepositLedger depositLedger, @MappingTarget final DepositLedgerReportWrapper wrapper ) {
		wrapper.setAccountNo(depositLedger.getAccountNo());
		wrapper.setBalanceAmount(depositLedger.getBalanceAmount());
		wrapper.setCreditAmount(depositLedger.getCreditAmount());
		wrapper.setDebitAmount(depositLedger.getDebitAmount());
		wrapper.setDepositDate1(depositLedger.getDepositDate());
		wrapper.setParticulars(depositLedger.getParticulars());
		wrapper.setNarration(depositLedger.getNarration());
		wrapper.setName(depositLedger.getDeposit().getMember().getFirstName()+""+depositLedger.getDeposit().getMember().getMiddleName()+""+depositLedger.getDeposit().getMember().getLastName());
		wrapper.setDepositDate(depositLedger.getDeposit().getDepositDate());
		wrapper.setDepositAmount(depositLedger.getDeposit().getDepositAmount());
		wrapper.setMaturityDate(depositLedger.getDeposit().getMaturityDate());
		wrapper.setMaturityAmount(depositLedger.getDeposit().getMaturityAmount());
		wrapper.setRecurringAmount(depositLedger.getDeposit().getRecurringAmount());
		wrapper.setReceiptNo(depositLedger.getDeposit().getReceiptNo());
	
	}
}
