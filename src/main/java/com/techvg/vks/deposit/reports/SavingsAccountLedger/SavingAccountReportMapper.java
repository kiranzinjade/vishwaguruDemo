package com.techvg.vks.deposit.reports.SavingsAccountLedger;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.techvg.vks.deposit.domain.DepositLedger;
import com.techvg.vks.deposit.domain.SavingAccount;
@Mapper(componentModel = "spring")
public interface SavingAccountReportMapper {

	
	public List<SavingAccountReportWrapper> mapAllDataList(List<SavingAccount> savingAccountList);

	public SavingAccountReportWrapper mapAllData(DepositLedger depositLedger);
	
	@AfterMapping
	default void fillInProperties(final DepositLedger depositLedger,
			@MappingTarget final SavingAccountReportWrapper wrapper) {
	}

}