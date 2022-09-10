package com.techvg.vks.trading.reports.PurchaseRegisterReport;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.reports.LoanDisbursementRegister.LoanDisbursementRegisterWrapper;
import com.techvg.vks.trading.domain.PurchaseRegister;

@Mapper(componentModel="spring")
public interface PurchaseRegisterReportMapper {

	@IterableMapping(qualifiedByName="all")
	List<PurchaseRegisterReportWrapper> mapAllPurchaseDataList(List<PurchaseRegister> purchaseRegister);
	

	@Named("all")
	@Mappings({

			@Mapping(ignore = true, target = "name"),

	})
	PurchaseRegisterReportWrapper mapAllFdData(PurchaseRegister purchaseRegister);

	@AfterMapping
	default void fillInProperties(final PurchaseRegister purchaseRegister,
			@MappingTarget final PurchaseRegisterReportWrapper wrapper) {
		wrapper.setOwnerName(purchaseRegister.getPurchaseOrder().getVendorRegister().getOwnerName());
		wrapper.setBalanceAmount(purchaseRegister.getPurchaseOrder().getBalanceAmount());
		wrapper.setBillAmount(purchaseRegister.getPurchaseOrder().getBillAmount());
		wrapper.setPurchaseDate(purchaseRegister.getPurchaseOrder().getPurchaseDate());
		wrapper.setOrderNo(purchaseRegister.getPurchaseOrder().getOrderNo());
		wrapper.setBillNo(purchaseRegister.getPurchaseOrder().getBillNo());
		wrapper.setName(purchaseRegister.getProduct().getName());
		
	}


}
