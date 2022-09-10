package com.techvg.vks.deposit.reports.AccountsOpenedClosed;

import com.techvg.vks.deposit.domain.SavingAccount;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SavingsAccountMapper {

	public List<AccountsOpenedClosedReportWrapper> mapAllSavingsDataList(List<SavingAccount> savingAccountList);

	public AccountsOpenedClosedReportWrapper mapSavingsData(SavingAccount savingAccount);
	
	@AfterMapping
	default void fillInProperties(final SavingAccount savingAccount,
			@MappingTarget final AccountsOpenedClosedReportWrapper wrapper) {

			wrapper.setAccountNumber(savingAccount.getAccountNo());
			wrapper.setDateOfOpening(savingAccount.getAccountOpeningDate());
			wrapper.setDateOfClosing(savingAccount.getAccountClosingDate());
			wrapper.setNameOfAccountHolder(savingAccount.getMember().getFirstName() + " " + savingAccount.getMember().getMiddleName()
					+ " " + savingAccount.getMember().getLastName());

	}
}
