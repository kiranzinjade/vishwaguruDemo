package com.techvg.vks.trading.reports.stockstatementreport;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueMappingStrategy;

import com.techvg.vks.config.TradingConstants;
import com.techvg.vks.trading.domain.StockRegister;

@Mapper(componentModel = "spring")
public interface StockStatementMapper {

	@IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
	public List<StockStatementWrapper> mapAllStockStatementDataList(List<StockRegister> stockRegisterDetailslist1);

	public StockStatementWrapper mapAllStockStatementData(StockRegister stockRegister);

	@AfterMapping
	default void fillInProperties(final StockRegister stockRegister,
			@MappingTarget final StockStatementWrapper wrapper) {

		wrapper.setOpeningStockQuantity(stockRegister.getCurrentStock());
		wrapper.setOpeningStockValue(stockRegister.getCurrentStockValue());
		wrapper.setClosingStockQuantity(stockRegister.getCurrentStock());
		wrapper.setClosingStockValue(stockRegister.getCurrentStockValue());
		wrapper.setTotal(stockRegister.getCurrentStock());
		if (stockRegister.getTransType().equalsIgnoreCase(TradingConstants.PURCHASE_TRANS)) {
			wrapper.setPurchaseQuantity(stockRegister.getQuantity());
			wrapper.setPurchaseValue(stockRegister.getProduct().getSellingPrice());
			wrapper.setSalesQuantity(0.0);
			wrapper.setImpairmentQuantity(0.0);
		} 
		 if (stockRegister.getTransType().equalsIgnoreCase(TradingConstants.IMPAIRMENT_TRANS)) {
				wrapper.setImpairmentQuantity(stockRegister.getQuantity());
				wrapper.setSalesQuantity(0.0);
				wrapper.setPurchaseQuantity(0.0);
				wrapper.setPurchaseValue(0.0);
			}
		else if (stockRegister.getTransType().equalsIgnoreCase(TradingConstants.SALES_TRANS)) {
			wrapper.setSalesQuantity(stockRegister.getQuantity());
			wrapper.setPurchaseQuantity(0.0);
			wrapper.setPurchaseValue(0.0);
			wrapper.setImpairmentQuantity(0.0);

		}
	
		
		wrapper.setDate(stockRegister.getTransactionDate());
		wrapper.setItemName(stockRegister.getProduct().getName());
//		wrapper.setTotal(stockRegister.getSalesQuantity() + stockRegister.getImpairmentQuantity());

	}

}
