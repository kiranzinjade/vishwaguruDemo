package com.techvg.vks.society.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.techvg.vks.society.model.PrerequisitesDto;
import com.techvg.vks.society.model.ProductTypeDto;
import com.techvg.vks.society.model.ProductTypePageList;

@Service
public interface ProductTypeService {

	ProductTypeDto addProductType(ProductTypeDto productTypeDto, Authentication authentication);

	ProductTypePageList listProductType(Pageable pageable);

	ProductTypeDto deleteProductTypeById(Long id);

	ProductTypeDto updateProductType(Long id, ProductTypeDto productTypeDto);

	ProductTypeDto getProductTypeById(Long id);
	
	List<ProductTypeDto> listAllProduct();

}
