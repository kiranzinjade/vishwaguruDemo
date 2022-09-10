package com.techvg.vks.trading.service;

import com.techvg.vks.deposit.model.DepositLedgerDto;
import com.techvg.vks.trading.domain.StockRegister;

import com.techvg.vks.trading.model.StockRegisterDto;
import com.techvg.vks.trading.model.StockRegisterPageList;

import java.util.List;
import java.util.Date;
import org.springframework.data.domain.Pageable;

public interface StockRegisterService {

	StockRegisterDto addToStockRegister(StockRegister stockRegister);
	
	StockRegisterPageList listStock(Pageable pageable);

	List<StockRegisterDto> getListById(Long productId);

	StockRegisterDto getCurrentStockById(Long productId);

	List<StockRegisterDto> getStockList(Long productId, String fromDate, String toDate);
	


}
