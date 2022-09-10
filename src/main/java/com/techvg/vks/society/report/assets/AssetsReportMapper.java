package com.techvg.vks.society.report.assets;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;

import com.techvg.vks.society.domain.AssetsRegistration;
import com.techvg.vks.trading.domain.StockRegister;
import com.techvg.vks.trading.reports.stockstatementreport.StockStatementWrapper;

@Mapper(componentModel="spring")

public interface AssetsReportMapper {

	@IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
	public List<AssetsReportWrapper> mapAllDataList(List<AssetsRegistration> assetsRegistrationDetailslist);
	
	public StockStatementWrapper mapAllStockStatementData(StockRegister stockRegister);
	
	
	@AfterMapping
	default void fillInProperties(final AssetsRegistration assetsRegistration, @MappingTarget final AssetsReportWrapper wrapper ) {
        
		wrapper.setDate(assetsRegistration.getDate());
		wrapper.setBillNo(assetsRegistration.getBillNo());
		if(assetsRegistration.getTransactionType().equalsIgnoreCase("purchase")) {
			wrapper.setPurchaseQuantity(assetsRegistration.getQuantity());
			wrapper.setPurchaseValue(assetsRegistration.getCost());
			wrapper.setSalesQuantity(0);
			wrapper.setSaleseValue(0.0);
		}else if(assetsRegistration.getTransactionType().equalsIgnoreCase("sales")) {
			wrapper.setSalesQuantity(assetsRegistration.getQuantity());
			wrapper.setSaleseValue(assetsRegistration.getCost());
			wrapper.setPurchaseQuantity(0);
			wrapper.setPurchaseValue(0.0);
		}
	
	}
}
