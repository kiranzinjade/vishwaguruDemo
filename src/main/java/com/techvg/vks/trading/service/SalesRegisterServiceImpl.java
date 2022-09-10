package com.techvg.vks.trading.service;


import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.trading.domain.Product;
import com.techvg.vks.trading.domain.SalesRegister;
import com.techvg.vks.trading.mapper.SalesRegisterMapper;
import com.techvg.vks.trading.model.SalesRegisterDto;
import com.techvg.vks.trading.model.SalesRegisterPageList;
import com.techvg.vks.trading.repository.ProductRepository;
import com.techvg.vks.trading.repository.SalesRegisterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalesRegisterServiceImpl implements SalesRegisterService{
	
	private final SalesRegisterMapper mapper;
	private final SalesRegisterRepository repo;
	private final ProductRepository productRepo;


	@Override
	public SalesRegisterDto addSales(SalesRegisterDto salesRegisterDto) {

		SalesRegister salesRegister=mapper.toDomain(salesRegisterDto);

		Product product=productRepo.findById(salesRegisterDto.getProductId())
				.orElseThrow(() -> new NotFoundException("No Product found for Id : " +salesRegisterDto.getProductId()));
		salesRegister.setProduct(product);

		return mapper.toDto(repo.save(salesRegister));
	
	}


	@Override
	public SalesRegisterPageList listSales(Pageable pageable) {
		Page<SalesRegister>  salesRegisterPage = repo.findByIsDeleted(pageable,false);

		return new SalesRegisterPageList(salesRegisterPage
				.stream()
				.map(mapper::toDto)
				.collect(Collectors.toList()),
				PageRequest
						.of(salesRegisterPage.getPageable().getPageNumber(),
								salesRegisterPage.getPageable().getPageSize()),
				salesRegisterPage.getTotalElements());
	}

	@Override
	public SalesRegisterDto deleteSalesById(Long id) {
		SalesRegister salesRegister = repo.findById(id).orElseThrow(
				() -> new NotFoundException("No Sales found for Id : " +id));
		if (salesRegister != null) {
			salesRegister.setIsDeleted(true);
			repo.save(salesRegister);
		}
		return mapper.toDto(salesRegister);
	}

	@Override
	public SalesRegisterDto getSalesById(Long id) {
		SalesRegister salesRegister = repo.findById(id).orElseThrow(
				() -> new NotFoundException("No Sales found for Id : " +id));

		return mapper.toDto(salesRegister);
	}
}


