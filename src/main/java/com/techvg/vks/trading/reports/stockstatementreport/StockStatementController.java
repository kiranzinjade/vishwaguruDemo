package com.techvg.vks.trading.reports.stockstatementreport;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/stockstatement")
public class StockStatementController {
	

	private final StockStatementService stockStatementService;
	
		
	@GetMapping({ "/{fromDate}/{productId}" })
	public ResponseEntity<?>getStockStatementReport(@PathVariable("fromDate") String fromDate,@PathVariable("productId") Long productId)
	{ 
			return stockStatementService.getStockStatementReport(fromDate,productId);	
	}

}
