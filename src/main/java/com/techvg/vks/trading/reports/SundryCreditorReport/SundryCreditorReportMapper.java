package com.techvg.vks.trading.reports.SundryCreditorReport;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;

import com.techvg.vks.trading.domain.SundryCreditor;
import com.techvg.vks.trading.domain.SundryCreditorTransaction;

@Mapper(componentModel="spring")
public interface SundryCreditorReportMapper {
	
	@IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
	List<SundryCreditorReportWrapper> mapAllSundryCreditorDataList(List<SundryCreditorTransaction> sundryCreditorlist);
	
	public SundryCreditorReportWrapper mapAllBorrowingLedgerData(SundryCreditor sundryCreditor);
	
	@AfterMapping
	default void fillInProperties(final SundryCreditorTransaction sundryCreditor, @MappingTarget final SundryCreditorReportWrapper wrapper ) {
		
		wrapper.setDate(sundryCreditor.getDate());
		wrapper.setDebit(sundryCreditor.getDebit());
		wrapper.setCredit(sundryCreditor.getCredit());
		wrapper.setBalance(sundryCreditor.getBalance());
		wrapper.setParticulars(sundryCreditor.getParticulars());
		wrapper.setTransDate(sundryCreditor.getTransactionDate());
	}
		



}
