package com.techvg.vks.trading.reports.PurchaseReturnReport;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.techvg.vks.trading.domain.PurchaseReturnDetails;
import com.techvg.vks.trading.domain.SalesRegister;
import com.techvg.vks.trading.reports.SalesRegisterReport.SalesRegisterReportWrapper;

@Mapper(componentModel="spring")
public interface PurchaseReturnReportMapper {

	@IterableMapping(qualifiedByName="all")
	List<PurchaseReturnReportWrapper> mapAllPurchaseReturnDataList(List<PurchaseReturnDetails> purchaseReturnDetails);
	

	@Named("all")
	@Mappings({

			@Mapping(ignore = true, target = "name"),

	})
	PurchaseReturnReportWrapper mapAllPurchaseData(PurchaseReturnDetails purchaseReturnDetails);

	@AfterMapping
	default void fillInProperties(final PurchaseReturnDetails purchaseReturnDetails,
			@MappingTarget final PurchaseReturnReportWrapper wrapper) {
		wrapper.setOwnerName(purchaseReturnDetails.getPurchaseReturn().getPurchaseOrder().getVendorRegister().getOwnerName());
		wrapper.setBalanceAmount(purchaseReturnDetails.getPurchaseReturn().getBalanceAmount());
		wrapper.setReturnAmt(purchaseReturnDetails.getPurchaseReturn().getReturnAmt());
		wrapper.setReturnDate(purchaseReturnDetails.getPurchaseReturn().getReturnDate());
		wrapper.setOrderNo(purchaseReturnDetails.getPurchaseReturn().getPurchaseOrder().getOrderNo());
		wrapper.setBillNo(purchaseReturnDetails.getPurchaseReturn().getBillNo());
		wrapper.setName(purchaseReturnDetails.getProduct().getName());
		
	}


}


