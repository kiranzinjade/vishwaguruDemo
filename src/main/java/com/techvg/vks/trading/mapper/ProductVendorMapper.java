package com.techvg.vks.trading.mapper;

import com.techvg.vks.trading.domain.ProductVendor;
import com.techvg.vks.trading.model.ProductVendorDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductVendorMapper {

    ProductVendor toDomain(ProductVendorDto dto);
    @Mapping(source="domain.product.name",target="prodName")
    @Mapping(source="domain.vendor.companyName",target="vendorName")
    @Mapping(source="domain.product.id",target="productId")
    @Mapping(source="domain.vendor.id",target="vendorId")
    ProductVendorDto toDto(ProductVendor domain);
}
