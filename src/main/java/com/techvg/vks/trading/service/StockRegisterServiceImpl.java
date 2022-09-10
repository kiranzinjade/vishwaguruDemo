package com.techvg.vks.trading.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techvg.vks.common.DateConverter;
import com.techvg.vks.config.TradingConstants;
import com.techvg.vks.deposit.domain.DepositLedger;
import com.techvg.vks.deposit.model.DepositLedgerDto;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.trading.domain.Product;
import com.techvg.vks.trading.domain.SalesOrder;
import com.techvg.vks.trading.domain.Stock;
import com.techvg.vks.trading.domain.StockRegister;
import com.techvg.vks.trading.mapper.StockRegisterMapper;
import com.techvg.vks.trading.model.StockRegisterDto;
import com.techvg.vks.trading.model.StockRegisterPageList;
import com.techvg.vks.trading.repository.ProductRepository;
import com.techvg.vks.trading.repository.StockRegisterRepository;
import com.techvg.vks.trading.repository.StockRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockRegisterServiceImpl implements StockRegisterService {

	private final StockRegisterRepository repo;
	private final StockRegisterMapper mapper;
	private final StockRepository stockRepo;
	private final ProductRepository productRepo;

	public StockRegisterDto addToStockRegister(StockRegister stockRegister) {

		Optional<StockRegister> optStockRegister = repo.findByProductId(stockRegister.getProduct().getId());
		if(optStockRegister.isPresent()){
			StockRegister existingStockRegister = optStockRegister.get();
			stockRegister = updateCurrentStockValue(stockRegister,existingStockRegister );
		}
		else{
			stockRegister.setCurrentStock(stockRegister.getQuantity());
			stockRegister.setCurrentStockValue(stockRegister.getProduct().getSellingPrice() * stockRegister.getQuantity());
			stockRegister.setTransactionDate(new Date());
			Stock stock=new Stock();
			Product product = productRepo.findById(stockRegister.getProduct().getId())
					.orElseThrow(() -> new NotFoundException("No Product found for Id : "));
			stock.setProduct(product);
			stock.setOpeningStockQuantity(stockRegister.getCurrentStock());
			stock.setOpeningStockValue(stockRegister.getProduct().getSellingPrice() * stockRegister.getQuantity());
			stockRepo.save(stock);
		}

		return mapper.toDto(repo.save(stockRegister));
	}

	private StockRegister updateCurrentStockValue(StockRegister stockRegister,StockRegister existingStockRegister ){
		double quantity = stockRegister.getQuantity();
		double price = stockRegister.getProduct().getSellingPrice();
		double currStock = existingStockRegister.getCurrentStock();
		double currStockValue = existingStockRegister.getCurrentStockValue();
		

		if((stockRegister.getTransType().equalsIgnoreCase(TradingConstants.PURCHASE_TRANS)) ||
		  (stockRegister.getTransType().equalsIgnoreCase(TradingConstants.SALES_RETURN_TRANS))){
			stockRegister.setCurrentStock(currStock +  quantity);
			stockRegister.setCurrentStockValue(currStockValue + (price * quantity));
			stockRegister.setTransactionDate(new Date());
		}

		if(stockRegister.getTransType().equalsIgnoreCase(TradingConstants.SALES_TRANS) ||
		   stockRegister.getTransType().equalsIgnoreCase(TradingConstants.IMPAIRMENT_TRANS) ||
			stockRegister.getTransType().equalsIgnoreCase(TradingConstants.PURCHASE_RETURN_TRANS)){
				stockRegister.setCurrentStock(currStock -  quantity);
				stockRegister.setCurrentStockValue(currStockValue - (price * quantity));
				stockRegister.setTransactionDate(new Date());

		}
		return stockRegister;
	}

	@Override
	public StockRegisterPageList listStock(Pageable pageable) {
		Page<StockRegister> stockPage;
		stockPage = repo.findByIsDeleted(pageable,false);

		return new StockRegisterPageList(stockPage
										.getContent()
										.stream()
										.map(mapper::toDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(stockPage.getPageable().getPageNumber(),
													stockPage.getPageable().getPageSize()),
											stockPage.getTotalElements());

	}

	@Override
	public List<StockRegisterDto> getListById(Long productId) {
		return mapper.toDto(repo.findByProductIdAndTransType(productId, TradingConstants.PURCHASE_TRANS));
	}
	
	

	@Override
	public StockRegisterDto getCurrentStockById(Long productId) {
		StockRegister stockRegister = repo.findByIdCurrentStock(productId);
        return mapper.toDto(stockRegister);
	}

	@Override
	public List<StockRegisterDto> getStockList(Long productId, String fromDate, String toDate) {
		
		  Date fromdate4=DateConverter.getDate(fromDate);
		  Date todate5=DateConverter.getDate(toDate);
		  List<StockRegister> stockRegister=repo.findByProductIdDateformatto(productId, fromdate4, todate5);
		  return mapper.toDto(stockRegister);
	}
}
