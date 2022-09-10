package com.techvg.vks.trading.service;

import com.techvg.vks.trading.model.ProductDto;
import com.techvg.vks.trading.model.ProductPageList;
import com.techvg.vks.trading.model.ProductVendorDto;
import com.techvg.vks.trading.model.VendorRegisterDto;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    ProductDto addProduct(ProductDto productDto, Authentication authentication);
    ProductDto updateProduct(Long productId, ProductDto productDto);
    ProductDto getProductById(Long productId);
    ProductDto deleteProductById(Long productId);
    ProductPageList listProducts(Pageable pageable);

    ProductVendorDto addVendor(Long vendorId, Long productId);
    boolean removeVendor(Long vendorId, Long productId);
    List<ProductDto> listAllProduct();
    
	List<ProductDto> productListByVendor(Long vendorId);


}
