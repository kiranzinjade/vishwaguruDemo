package com.techvg.vks.trading.service;

import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.MeasuringUnit;
import com.techvg.vks.society.domain.ProductType;
import com.techvg.vks.society.repository.MeasuringUnitRepository;
import com.techvg.vks.society.repository.ProductTypeRepository;
import com.techvg.vks.trading.domain.Product;
import com.techvg.vks.trading.domain.ProductVendor;
import com.techvg.vks.trading.domain.VendorRegister;
import com.techvg.vks.trading.mapper.ProductMapper;
import com.techvg.vks.trading.mapper.ProductVendorMapper;
import com.techvg.vks.trading.mapper.VendorRegisterMapper;
import com.techvg.vks.trading.model.ProductDto;
import com.techvg.vks.trading.model.ProductPageList;
import com.techvg.vks.trading.model.ProductVendorDto;
import com.techvg.vks.trading.model.VendorRegisterDto;
import com.techvg.vks.trading.repository.ProductRepository;
import com.techvg.vks.trading.repository.ProductVendorRepository;
import com.techvg.vks.trading.repository.VendorRegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductTypeRepository productTypeRepository;
    private final MeasuringUnitRepository measuringUnitRepository;
    private final VendorRegisterRepository vendorRegisterRepository;
    private final VendorRegisterMapper vendorRegisterMapper;
    private final ProductVendorRepository productVendorRepo;
    private final ProductVendorMapper vendorMapper;

    @Override
    @Transactional
    public ProductDto addProduct(ProductDto productDto, Authentication authentication) {
        Optional<Product> productOptional = productRepository.findByNameAndIsDeleted(productDto.getName(),false);
        if (productOptional.isPresent()){
            throw new AlreadyExitsException("Product already exists : " + productDto.getName());
        }
        else {
            ProductType productType = productTypeRepository.findById(productDto.getProdTypeId()).orElseThrow(
                    () -> new NotFoundException("No Product type found for Id :  " +productDto.getProdTypeId()));
            MeasuringUnit measuringUnit = measuringUnitRepository.findById(productDto.getUnitId()).orElseThrow(
                    () -> new NotFoundException("No Measuring Unit found for Id : " +productDto.getUnitId()));
            VendorRegister vendorRegister = vendorRegisterRepository.findById(productDto.getVendorId()).orElseThrow(
                    () -> new NotFoundException("No Vendor found for Id : " +productDto.getVendorId()));

            Product product = productMapper.toDomain(productDto);
            product.setMeasuringUnit(measuringUnit);
            product.setProductType(productType);

            ProductVendor productVendor = new ProductVendor();
            productVendor.setProduct(product);
            productVendor.setVendor(vendorRegister);
            productVendorRepo.save(productVendor);
            product.addVendor(productVendor);

            productRepository.save(product);

            return productMapper.toDto(product);

        }
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductDto productDto) {

        productRepository.findById(productId).orElseThrow(() -> new NotFoundException("No Product found for Id : " +productId));
        
        ProductType productType = productTypeRepository.findById(productDto.getProdTypeId()).orElseThrow(
                () -> new NotFoundException("No Product type found for Id :  " +productDto.getProdTypeId()));
      
        MeasuringUnit measuringUnit = measuringUnitRepository.findById(productDto.getUnitId()).orElseThrow(
                () -> new NotFoundException("No Measuring Unit found for Id : " +productDto.getUnitId()));
//        VendorRegister vendorRegister = vendorRegisterRepository.findById(productDto.getVendorId()).orElseThrow(
//                () -> new NotFoundException("No Vendor found for Id : " +productDto.getVendorId()));

        Product product = productMapper.toDomain(productDto);
        product.setId(productId);
        product.setMeasuringUnit(measuringUnit);
        product.setProductType(productType);

        ProductVendor productVendor = new ProductVendor();
        productVendor.setProduct(product);
     //   productVendor.setVendor(vendorRegister);
       // productVendorRepo.save(productVendor);
      //  product.addVendor(productVendor);

        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public ProductDto getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NotFoundException("No Product found for Id : " +productId));
        return productMapper.toDto(product);
    }

    @Override
    public ProductDto deleteProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(NotFoundException::new);
        if (product != null) {
            product.setIsDeleted(true);
            productRepository.save(product);
        }
        return productMapper.toDto(product);
    }

    @Override
    public ProductPageList listProducts(Pageable pageable) {
        Page<Product> productPage;
      //  productPage = productRepository.findAll(pageable);
        productPage = productRepository.findByisDeleted(pageable, false);

        return new ProductPageList(productPage
                .getContent()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList()),
                PageRequest
                        .of(productPage.getPageable().getPageNumber(),
                                productPage.getPageable().getPageSize()),
                productPage.getTotalElements());
    }

    @Override
    public ProductVendorDto addVendor(Long vendorId, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NotFoundException("No Product found for Id : " +productId));

        VendorRegister vendorRegister = vendorRegisterRepository.findById(vendorId).orElseThrow(
                () -> new NotFoundException("No Vendor found for Id : " +vendorId));
        
        Optional<ProductVendor> vendorOptio=productVendorRepo.findByVendorIdAndProductId(vendorId, productId);
        if(vendorOptio.isPresent()) {
        	throw new AlreadyExitsException("Vendor already assigned : "+vendorRegister.getCompanyName());
        }

        ProductVendor productVendor = new ProductVendor();
        productVendor.setProduct(product);
        productVendor.setVendor(vendorRegister);
        productVendorRepo.save(productVendor);
        product.addVendor(productVendor);

        productRepository.save(product);
        return vendorMapper.toDto(productVendor);
    }

    @Override
    @Transactional
    public boolean removeVendor(Long vendorId, Long productId) {
    	
    	List<ProductVendor>s= productVendorRepo.findByProductId(productId);
    	System.out.println("listtttttttttttttttttttttttttttt"+s);
    	if(s.size()>1) {
    		System.out.println("iffffffffffffff");
        productVendorRepo.removeProductVendor(vendorId, productId);
        return true;
    	}
    	else {
    		System.out.println("elsssssssssssssssssssssss");
    		return false;
    	}
       // return true;
    }

	@Override
	public List<ProductDto> listAllProduct() {
		return productMapper.domainToDtoList(productRepository.findByisDeleted(false));

	}

	@Override
	public List<ProductDto> productListByVendor(Long vendorId) {
		// TODO Auto-generated method stub
		List<Product>list=productRepository.findByVendorId(vendorId);
		System.out.println("list product"+list);
		return productMapper.domainToDtoList(productRepository.findByVendorId(vendorId));
	}
	

}
