package com.techvg.vks.trading.mapper;

import com.techvg.vks.trading.domain.Product;
import com.techvg.vks.trading.model.ProductDto;
import com.techvg.vks.trading.model.ProductVendorDto;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toDomain(ProductDto dto);

    @Mapping(source = "domain.measuringUnit.name", target = "unit")
    @Mapping(source = "domain.productType.type", target = "prodType")
    @Mapping(source = "domain.measuringUnit.id", target = "unitId")
    @Mapping(source = "domain.productType.id", target = "prodTypeId")
    ProductDto toDto(Product domain);

    List<ProductDto> domainToDtoList(List<Product> domainList);
    
    @AfterMapping
    default void updateDto(@MappingTarget ProductDto dto, Product domain ) {
        List<ProductVendorDto> vendors = new ArrayList<>();
        domain.getProductVendors().forEach(productVendor -> {
            ProductVendorDto pvDto = new ProductVendorDto();
            pvDto.setProdName(productVendor.getProduct().getName());
            pvDto.setVendorName(productVendor.getVendor().getCompanyName());
            pvDto.setProductId(productVendor.getProduct().getId());
            pvDto.setVendorId(productVendor.getVendor().getId());
            vendors.add(pvDto);
        });

        dto.setVendors(vendors);

    }
}
