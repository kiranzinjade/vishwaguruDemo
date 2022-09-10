package com.techvg.vks.trading.reports.SalesRegisterReport;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.techvg.vks.trading.domain.PurchaseRegister;
import com.techvg.vks.trading.domain.SalesRegister;
import com.techvg.vks.trading.reports.PurchaseRegisterReport.PurchaseRegisterReportWrapper;

@Mapper(componentModel="spring")
public interface SalesRegisterReportMapper {

	@IterableMapping(qualifiedByName="all")
	List<SalesRegisterReportWrapper> mapAllSalesDataList(List<SalesRegister> salesRegister);
	

	@Named("all")
	@Mappings({

			@Mapping(ignore = true, target = "name"),

	})
	SalesRegisterReportWrapper mapAllFdData(SalesRegister salesRegister);

	@AfterMapping
	default void fillInProperties(final SalesRegister salesRegister,
			@MappingTarget final SalesRegisterReportWrapper wrapper) {
		wrapper.setFullName(salesRegister.getSalesOrder().getMember().getFirstName()+" "+salesRegister.getSalesOrder().getMember().getMiddleName()+" "+salesRegister.getSalesOrder().getMember().getLastName());
		wrapper.setBalanceAmount(salesRegister.getSalesOrder().getBalanceAmount());
		wrapper.setBillAmount(salesRegister.getSalesOrder().getBillAmount());
		wrapper.setSalesDate(salesRegister.getSalesOrder().getSalesDate());
		wrapper.setOrderNo(salesRegister.getSalesOrder().getOrderNo());
		wrapper.setBillNo(salesRegister.getSalesOrder().getBillNo());
		wrapper.setName(salesRegister.getProduct().getName());
		
	}


}
