package com.techvg.vks.trading.service;

import com.techvg.vks.config.TradingConstants;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.trading.domain.*;
import com.techvg.vks.trading.mapper.SalesReturnDetailsMapper;
import com.techvg.vks.trading.mapper.SalesReturnMapper;
import com.techvg.vks.trading.model.SalesReturnDto;
import com.techvg.vks.trading.model.SalesReturnPageList;
import com.techvg.vks.trading.repository.ProductRepository;
import com.techvg.vks.trading.repository.SalesOrderRepository;
import com.techvg.vks.trading.repository.SalesReturnDetailsRepository;
import com.techvg.vks.trading.repository.SalesReturnRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalesReturnServiceImpl implements SalesReturnService {

	private final ProductRepository productRepo;
	private final SalesOrderRepository orderRepo;
	private final SalesReturnRepository repo;
	private final SalesReturnMapper mapper;
	private final SalesReturnDetailsMapper detailsMapper;
	private final SalesReturnDetailsRepository salesReturnDetailsRepository;

	private final StockRegisterService stockService;

	@Override
	public SalesReturnDto addSalesReturn(SalesReturnDto salesReturnDto) {

		

		SalesReturn salesReturn = mapper.toDomain(salesReturnDto);

		SalesOrder order = orderRepo.findById(salesReturnDto.getSalesOrderId()).orElseThrow(
				() -> new NotFoundException("No Purchase Order found for Id : " + salesReturnDto.getSalesOrderId()));

		salesReturn.setSalesOrder(order);


		Set<SalesReturnDetails> returnDetails = new HashSet<>();

		salesReturnDto.getSalesReturnDetails().forEach(detailsDto ->{
			
				Product product = productRepo.findById(detailsDto.getProductId()).orElseThrow(
					() -> new NotFoundException("No Product Order found for Id : " + detailsDto.getProductId()));
			SalesReturnDetails detailsDomain = detailsMapper.toDomain(detailsDto);
			detailsDomain.setSalesReturn(salesReturn);
			detailsDomain.setProduct(product);
			returnDetails.add(detailsDomain);
			
		});

		salesReturn.setSalesReturnDetails(returnDetails);
		
		repo.save(salesReturn);
		updateStockRegister(salesReturn);
		return mapper.toDto(salesReturn);
	}

	private void updateStockRegister(SalesReturn salesReturn){
		salesReturn.getSalesReturnDetails().forEach(returnProd ->{
			StockRegister stockRegister = detailsMapper.toStockRegisterDomain(returnProd);
			if(returnProd.getIsDefective()){
				stockRegister.setTransType(TradingConstants.IMPAIRMENT_TRANS);

			}else {
				stockRegister.setTransType(TradingConstants.SALES_RETURN_TRANS);
			}
			stockService.addToStockRegister(stockRegister);
		});
	}

	@Override
	public SalesReturnPageList listAllSalesReturn(Pageable pageable) {
		Page<SalesReturn> salesReturnPage = repo.findAllByIsDeleted( pageable,false);

		return new SalesReturnPageList(salesReturnPage
				.getContent()
				.stream()
				.map(mapper::toDto)
				.collect(Collectors.toList()),
				PageRequest
						.of(salesReturnPage.getPageable().getPageNumber(),
								salesReturnPage.getPageable().getPageSize()),
				salesReturnPage.getTotalElements());
	}

	@Override
	public SalesReturnPageList listByMember(Pageable pageable, Long memberId) {
		Page<SalesReturn> salesReturnPage = repo.findAllBySalesOrder_Member_Id( pageable,memberId);

		return new SalesReturnPageList(salesReturnPage
				.getContent()
				.stream()
				.map(mapper::toDto)
				.collect(Collectors.toList()),
				PageRequest
						.of(salesReturnPage.getPageable().getPageNumber(),
								salesReturnPage.getPageable().getPageSize()),
				salesReturnPage.getTotalElements());
	}

	@Override
	public List<SalesReturnDto> getSalesReturnByLastRecords() {
		List<SalesReturn> salesReturn = repo.findLastRecords();
		return mapper.toDtoList(salesReturn);
	}

}
