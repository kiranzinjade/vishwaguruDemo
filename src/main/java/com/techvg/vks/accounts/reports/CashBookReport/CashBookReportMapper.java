package com.techvg.vks.accounts.reports.CashBookReport;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import com.techvg.vks.accounts.domain.CashBook;

@Mapper(componentModel="spring")
public interface CashBookReportMapper {

	@IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
	public List<CashBookReportWrapper> mapAllCashBookList(List<CashBook> cashBookList);
	public CashBookReportWrapper mapAllCashBook(CashBook cashBook);

}
