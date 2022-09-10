package com.techvg.vks.trading.service;

import com.techvg.vks.trading.domain.Product;
import com.techvg.vks.trading.domain.Stock;
import com.techvg.vks.trading.model.StockDto;

public interface StockService {

	Stock updateOpeningClosing(Long productId);

//	Stock updateOpeningClosing(Long product, Stock stock);



}
