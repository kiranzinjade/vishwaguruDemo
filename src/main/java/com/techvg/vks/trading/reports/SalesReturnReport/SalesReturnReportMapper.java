package com.techvg.vks.trading.reports.SalesReturnReport;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.techvg.vks.trading.domain.SalesRegister;
import com.techvg.vks.trading.domain.SalesReturnDetails;
import com.techvg.vks.trading.reports.PurchaseRegisterReport.PurchaseRegisterReportWrapper;
import com.techvg.vks.trading.reports.SalesRegisterReport.SalesRegisterReportWrapper;

@Mapper(componentModel="spring")
public interface SalesReturnReportMapper {

	@IterableMapping(qualifiedByName="all")
	List<SalesReturnReportWrapper> mapAllSalesReturnDataList(List<SalesReturnDetails> salesReturnDetails);
	

	@Named("all")
	@Mappings({

			@Mapping(ignore = true, target = "name"),

	})
	SalesReturnReportWrapper mapAllFdData(SalesReturnDetails salesReturnDetails);

	@AfterMapping
	default void fillInProperties(final SalesReturnDetails salesReturnDetails,
			@MappingTarget final SalesReturnReportWrapper wrapper) {
		wrapper.setFullName(salesReturnDetails.getSalesReturn().getSalesOrder().getMember().getFirstName()+" "+salesReturnDetails.getSalesReturn().getSalesOrder().getMember().getMiddleName()+" "+salesReturnDetails.getSalesReturn().getSalesOrder().getMember().getLastName());
		wrapper.setBalanceAmount(salesReturnDetails.getSalesReturn().getBalanceAmount());
		wrapper.setReturnAmt(salesReturnDetails.getSalesReturn().getReturnAmt());
		wrapper.setReturnDate(salesReturnDetails.getSalesReturn().getReturnDate());
		wrapper.setOrderNo(salesReturnDetails.getSalesReturn().getSalesOrder().getOrderNo());
		wrapper.setBillNo(salesReturnDetails.getSalesReturn().getBillNo());
		wrapper.setName(salesReturnDetails.getProduct().getName());
		
		
	}


}

