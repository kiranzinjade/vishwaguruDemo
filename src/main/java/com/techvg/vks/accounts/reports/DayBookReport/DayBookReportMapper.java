package com.techvg.vks.accounts.reports.DayBookReport;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import com.techvg.vks.accounts.domain.DayBook;

@Mapper(componentModel="spring")
public interface DayBookReportMapper {

	//@IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
	public List<DayBookReportWrapper> mapAllDayBookList(List<DayBook> dayBookList);
	public DayBookReportWrapper mapAllDayBook(DayBook dayBook);
}
