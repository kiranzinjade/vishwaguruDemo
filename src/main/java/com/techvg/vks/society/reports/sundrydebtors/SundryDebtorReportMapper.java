package com.techvg.vks.society.reports.sundrydebtors;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.techvg.vks.society.domain.SocietyBankTransaction;
import com.techvg.vks.society.report.bankbook.BankbookReportWrapper;
import com.techvg.vks.trading.domain.SundryDebtor;
import com.techvg.vks.trading.domain.SundryDebtorTransaction;

@Mapper(componentModel = "spring")

public interface SundryDebtorReportMapper {
	public List<SundryDebtorReportWrapper> mapAllDataList(List<SundryDebtorTransaction> sundryDebtorlist);

	public SundryDebtorReportWrapper mapAllData(SundryDebtor sundryDebtor);

	@AfterMapping
	default void fillInProperties(final SundryDebtorTransaction sundryDebtor,
			@MappingTarget final SundryDebtorReportWrapper wrapper) {

	
		wrapper.setDate(sundryDebtor.getDate());
		wrapper.setParticulars(sundryDebtor.getParticulars());
		wrapper.setTransactionDate(sundryDebtor.getTransactionDate());
		wrapper.setDebit(sundryDebtor.getDebit());
		if (sundryDebtor.getDebit() == null) {
			wrapper.setDebit(0.0);
		}
		wrapper.setCredit(sundryDebtor.getCredit());
		if (sundryDebtor.getCredit() == null) {
			wrapper.setCredit(0.0);

		}
		wrapper.setBalance(sundryDebtor.getBalance());

	}

}
