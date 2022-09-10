package com.techvg.vks.deposit.reports.AccountsOpenedClosed;

import com.techvg.vks.deposit.domain.Deposit;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
@Mapper(componentModel = "spring")
public interface AccountsOpenedClosedReportMapper {

	public List<AccountsOpenedClosedReportWrapper> mapAllDepositDataList(List<Deposit> depositList);

	public AccountsOpenedClosedReportWrapper mapDepositData(Deposit deposit);

	@AfterMapping
	default void fillInProperties(final Deposit deposit,
			@MappingTarget final AccountsOpenedClosedReportWrapper wrapper) {

			wrapper.setAccountNumber(deposit.getAccountNo());
			wrapper.setDateOfOpening(deposit.getDepositDate());
			wrapper.setDateOfClosing(deposit.getDepositClosingDate());
			wrapper.setNameOfAccountHolder(deposit.getMember().getFirstName() + " " + deposit.getMember().getMiddleName()
					+ " " + deposit.getMember().getLastName());

	}
}
