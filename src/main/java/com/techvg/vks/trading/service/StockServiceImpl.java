package com.techvg.vks.trading.service;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.trading.domain.Product;
import com.techvg.vks.trading.domain.Stock;
import com.techvg.vks.trading.domain.StockRegister;
import com.techvg.vks.trading.model.StockDto;
import com.techvg.vks.trading.repository.ProductRepository;
import com.techvg.vks.trading.repository.StockRegisterRepository;
import com.techvg.vks.trading.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockServiceImpl implements StockService {

	private final StockRepository stockRepository;
//	private final StockRegisterRepository stockRegisterReository;
	private final StockRegisterRepository stockRegisterRepository;
	private final ProductRepository productRepository;

	@Override
	public Stock updateOpeningClosing(Long productId) {
		Calendar cal = Calendar.getInstance();
		 Date currdateStart=new Date();
		 cal.set(Calendar.HOUR, 00);
		 cal.set(Calendar.MINUTE, 00);
		 cal.set(Calendar.MILLISECOND, 00);
		    currdateStart = cal.getTime();
		    @SuppressWarnings("deprecation")
			Timestamp start=new Timestamp(currdateStart.getYear(),cal.get(Calendar.MONTH),
		    		cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE),
		    		cal.get(Calendar.SECOND),cal.get(Calendar.MILLISECOND));	
		  
		    Date nextDateStart=new Date();
		    cal.add(Calendar.DAY_OF_YEAR, 1);
		    cal.set(Calendar.HOUR, 00);
		    cal.set(Calendar.MINUTE, 00);
		    cal.set(Calendar.MILLISECOND, 00);
		    nextDateStart=cal.getTime();
		    @SuppressWarnings("deprecation")
		    Timestamp end=new Timestamp(nextDateStart.getYear(),cal.get(Calendar.MONTH),
		    		cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE),
		    		cal.get(Calendar.SECOND),cal.get(Calendar.MILLISECOND));
		    
	    nextDateStart = cal.getTime();
	    
//		List<StockRegister> stockRegister = stockRegisterRepository.findByDate(productId, start,end);
//		System.out.println(stockRegister);
		
		StockRegister stockRegister1 = stockRegisterRepository.findLastRecordByDate(productId,nextDateStart);
		Product product = productRepository.findById(productId).orElseThrow(NotFoundException::new);
		Optional<Stock> currDateStockopt = stockRepository.findByLastRecord(productId);
		
   	 Stock currDateStock=new Stock();
        	 if(currDateStockopt.isPresent()) {
        	  currDateStock=currDateStockopt.get();
          	 currDateStock.setClosingStockQuantity(stockRegister1.getCurrentStock() );
 		      currDateStock.setClosingStockValue(stockRegister1.getCurrentStockValue() );
 		     currDateStock = stockRepository.save(currDateStock); 	 
         }
		
         Stock stockOpen = new Stock();
		stockOpen.setOpeningStockQuantity(currDateStock.getClosingStockQuantity());
		stockOpen.setOpeningStockValue(currDateStock.getClosingStockValue());
		stockOpen.setProduct(product);
		

		return stockRepository.save(stockOpen);

	}

}
