package com.techvg.vks.trading.service;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.trading.domain.Product;
import com.techvg.vks.trading.domain.PurchaseRegister;
import com.techvg.vks.trading.mapper.PurchaseRegisterMapper;
import com.techvg.vks.trading.model.PurchaseRegisterDto;
import com.techvg.vks.trading.model.PurchaseRegisterPageList;
import com.techvg.vks.trading.repository.ProductRepository;
import com.techvg.vks.trading.repository.PurchaseRegisterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseRegisterServiceImpl implements PurchaseRegisterService {
	
	private final PurchaseRegisterMapper  mapper;
	private final PurchaseRegisterRepository  repo;
	private final ProductRepository productRepository;

	
	@Override
	public PurchaseRegisterDto addPurchase(PurchaseRegisterDto purchaseRegisterDto,Authentication authentication) {
		PurchaseRegister purchaseRegister=mapper.toDomain(purchaseRegisterDto);

		Product product=productRepository.findById(purchaseRegisterDto.getProductId()).orElseThrow(NotFoundException::new);
		purchaseRegister.setProduct(product);

		return mapper.toDto(repo.save(purchaseRegister));
	}

	@Override
	public PurchaseRegisterPageList listPurchase(Pageable pageable) {
		log.debug("Request to get Purchase : {}");

		Page<PurchaseRegister> purchasePage;
		purchasePage = repo.findByIsDeleted(pageable,false);

		return new PurchaseRegisterPageList(purchasePage
										.stream()
										.map(mapper::toDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(purchasePage.getPageable().getPageNumber(),
													purchasePage.getPageable().getPageSize()),
											purchasePage.getTotalElements());

	}

	@Override
	public PurchaseRegisterDto deletePurchaseById(Long id) {
		PurchaseRegister purchaseRegister = repo.findById(id).orElseThrow(NotFoundException::new);
		if (purchaseRegister != null) {
			purchaseRegister.setIsDeleted(true);
			repo.save(purchaseRegister);
		}
	return mapper.toDto(purchaseRegister);
	}

	@Override
	public PurchaseRegisterDto getPurchaseById(Long id) {
		log.debug("REST request to get Purchase : {}", id);
		PurchaseRegister purchaseRegister = repo.findById(id).orElseThrow(
				() -> new NotFoundException("No Purchase found for Id : " +id));

		return mapper.toDto(purchaseRegister);
	}

}
