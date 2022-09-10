package com.techvg.vks.trading.service;

import com.techvg.vks.config.TradingConstants;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.trading.domain.*;
import com.techvg.vks.trading.mapper.PurchaseReturnDetailsMapper;
import com.techvg.vks.trading.mapper.PurchaseReturnMapper;
import com.techvg.vks.trading.model.PurchaseOrderDto;
import com.techvg.vks.trading.model.PurchaseReturnDto;
import com.techvg.vks.trading.model.PurchaseReturnPageList;
import com.techvg.vks.trading.repository.ProductRepository;
import com.techvg.vks.trading.repository.PurchaseOrderRepository;
import com.techvg.vks.trading.repository.PurchaseReturnRepository;
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

public class PurchaseReturnServiceImpl implements PurchaseReturnService{
	
	private final ProductRepository productRepo;
	private final PurchaseOrderRepository orderRepo;
	private final PurchaseReturnRepository repo;
	private final PurchaseReturnMapper mapper;
	private final PurchaseReturnDetailsMapper detailsMapper;

	private final StockRegisterService stockService;
	
	@Override
	public PurchaseReturnDto addPurchaseReturn(PurchaseReturnDto purchaseReturnDto) {

		PurchaseReturn purchaseReturn = mapper.toDomain(purchaseReturnDto);

		PurchaseOrder order = orderRepo.findById(purchaseReturnDto.getPurchaseOrderId()).orElseThrow(
				() -> new NotFoundException("No Purchase Order found for Id : " + purchaseReturnDto.getPurchaseOrderId()));

		purchaseReturn.setPurchaseOrder(order);

		Set<PurchaseReturnDetails> returnDetails = new HashSet<>();

		purchaseReturnDto.getPurchaseReturnDetails().forEach(detailsDto ->{

			Product product = productRepo.findById(detailsDto.getProductId()).orElseThrow(
					() -> new NotFoundException("No Product Order found for Id : " + detailsDto.getProductId()));

			PurchaseReturnDetails detailsDomain = detailsMapper.toDomain(detailsDto);
			detailsDomain.setPurchaseReturn(purchaseReturn);
			detailsDomain.setProduct(product);
			returnDetails.add(detailsDomain);
		});

		purchaseReturn.setPurchaseReturnDetails(returnDetails);

		repo.save(purchaseReturn);

		updateStockRegister(purchaseReturn);

		return mapper.toDto(purchaseReturn);
	}

	private void updateStockRegister(PurchaseReturn purchaseReturn){
		purchaseReturn.getPurchaseReturnDetails().forEach(returnProd ->{
			StockRegister stockRegister = detailsMapper.toStockRegisterDomain(returnProd);
			stockRegister.setTransType(TradingConstants.PURCHASE_RETURN_TRANS);
			stockService.addToStockRegister(stockRegister);
		});
	}

	@Override
	public PurchaseReturnPageList listAllPurchaseReturn(Pageable pageable) {
		Page<PurchaseReturn> purchaseReturnPage = repo.findAllByIsDeleted( pageable,false);

		return new PurchaseReturnPageList(purchaseReturnPage
				.getContent()
				.stream()
				.map(mapper::toDto)
				.collect(Collectors.toList()),
				PageRequest
						.of(purchaseReturnPage.getPageable().getPageNumber(),
								purchaseReturnPage.getPageable().getPageSize()),
				purchaseReturnPage.getTotalElements());
	}

	@Override
	public PurchaseReturnPageList listByVendor(Pageable pageable, Long vendorId) {
		Page<PurchaseReturn> purchaseReturnPage = repo.findAllByPurchaseOrder_VendorRegister_Id( pageable,vendorId);

		return new PurchaseReturnPageList(purchaseReturnPage
				.getContent()
				.stream()
				.map(mapper::toDto)
				.collect(Collectors.toList()),
				PageRequest
						.of(purchaseReturnPage.getPageable().getPageNumber(),
								purchaseReturnPage.getPageable().getPageSize()),
				purchaseReturnPage.getTotalElements());
	}

	@Override
	public List<PurchaseReturnDto> getPurchaseReturnByLastRecords() {
		
			List<PurchaseReturn> purchaseReturn = repo.findLastRecords();
			return mapper.toDtoList(purchaseReturn);
		}
	


}
