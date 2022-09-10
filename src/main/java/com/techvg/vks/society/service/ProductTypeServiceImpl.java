package com.techvg.vks.society.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.ProductType;
import com.techvg.vks.society.mapper.ProductTypeMapper;
import com.techvg.vks.society.model.ProductTypeDto;
import com.techvg.vks.society.model.ProductTypePageList;
import com.techvg.vks.society.repository.ProductTypeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductTypeServiceImpl implements ProductTypeService {
	
	private final ProductTypeMapper  productTypeMapper;
	private final ProductTypeRepository  productTypeRepository;
	
	@Override
	public ProductTypeDto addProductType(ProductTypeDto productTypeDto, Authentication authentication) {
		Optional <ProductType>  productTypeOptional= productTypeRepository.findByTypeAndIsDeleted(productTypeDto.getType(), false);
		if(productTypeOptional.isPresent()) {
			throw new AlreadyExitsException("Product Type exists :" + productTypeDto.getType());
		}
		else {
		
		//log.debug("REST request to save Product Type : {}", productTypeDto);
		return productTypeMapper.productTypeToProductTypeDto(productTypeRepository.save(productTypeMapper.productTypeDtoToProductType(productTypeDto)));
	}
	}
	@Override
	public ProductTypePageList listProductType(Pageable pageable) {
	
		log.debug("REST request to save Product Type : {}");
		
		Page<ProductType> productTypePage;
		productTypePage = productTypeRepository.findByisDeleted(pageable,false);
       
		
		return new ProductTypePageList(productTypePage
										.getContent()
										.stream()
										.map(productTypeMapper::productTypeToProductTypeDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(productTypePage.getPageable().getPageNumber(),
													productTypePage.getPageable().getPageSize()),
										productTypePage.getTotalElements());
	}

	@Override
	public ProductTypeDto deleteProductTypeById(Long id) {
		ProductType productType = productTypeRepository.findById(id).orElseThrow(NotFoundException::new);
		if (productType != null) {
			productType.setIsDeleted(true);
			productTypeRepository.save(productType);
		}
	return productTypeMapper.productTypeToProductTypeDto(productType);
	}

	@Override
	public ProductTypeDto updateProductType(Long id, ProductTypeDto productTypeDto) {
		ProductType productType = productTypeRepository.findById(id).orElseThrow(NotFoundException::new);
		productTypeDto.setId(productType.getId());	
		ProductType productType1 = productTypeMapper.productTypeDtoToProductType(productTypeDto);
		return productTypeMapper.productTypeToProductTypeDto(productTypeRepository.save(productType1));
	}

	@Override
	public ProductTypeDto getProductTypeById(Long id) {
		log.debug("REST request to get Product Type : {}", id);
		ProductType productType = productTypeRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No Product Setting found for Id : " +id));

		return productTypeMapper.productTypeToProductTypeDto(productType);
	}

	@Override
	public List<ProductTypeDto> listAllProduct() {
		// TODO Auto-generated method stub
		

		return productTypeMapper.domainToDtoList(productTypeRepository.findByisDeleted(false));
	}

	
}
