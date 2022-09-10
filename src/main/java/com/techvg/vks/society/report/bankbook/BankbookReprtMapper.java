package com.techvg.vks.society.report.bankbook;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.techvg.vks.society.domain.SocietyBankTransaction;

@Mapper(componentModel = "spring")
public interface BankbookReprtMapper {
	public List<BankbookReportWrapper> mapAllDataList(List<SocietyBankTransaction> societyBankTransactionlist);

	public BankbookReportWrapper mapAllData(SocietyBankTransaction societyBankTransaction);

	@AfterMapping
	default void fillInProperties(final SocietyBankTransaction societyBankTransaction,
			@MappingTarget final BankbookReportWrapper wrapper) {

		wrapper.setBankName(societyBankTransaction.societyBank.getBankName());
		wrapper.setAccountType(societyBankTransaction.societyBank.getAccountType());
		wrapper.setAccountNumber(societyBankTransaction.societyBank.getAccountNumber());
		wrapper.setDate(societyBankTransaction.getDate());
		wrapper.setParticulars(societyBankTransaction.getParticulars());
		wrapper.setTransactionDate(societyBankTransaction.getTransactionDate());
		wrapper.setDebit(societyBankTransaction.getDebit());
		if (societyBankTransaction.getDebit() == null) {
			wrapper.setDebit(0.0);
		}
		wrapper.setCredit(societyBankTransaction.getCredit());
		if (societyBankTransaction.getCredit() == null) {
			wrapper.setCredit(0.0);

		}
		wrapper.setBalance(societyBankTransaction.getBalance());
		wrapper.setNarration(societyBankTransaction.getNarration());
		wrapper.setInitial(societyBankTransaction.getInitial());

	}

}
