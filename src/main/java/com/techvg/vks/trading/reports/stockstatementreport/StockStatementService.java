package com.techvg.vks.trading.reports.stockstatementreport;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface StockStatementService {
	
    ResponseEntity<?> getStockStatementReport(String fromDate,Long productId);
	
	byte[] creatingStockStatementReport(String fromDate,Long productId);

}
