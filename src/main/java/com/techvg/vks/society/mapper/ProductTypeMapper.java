package com.techvg.vks.society.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.techvg.vks.society.domain.ProductType;
import com.techvg.vks.society.model.ProductTypeDto;
import com.techvg.vks.trading.mapper.SalesRegisterMapper;

@Mapper(uses = { SalesRegisterMapper.class}, componentModel = "spring")
public interface ProductTypeMapper {

  //  @Mapping(source = "producttype.salesRegister", target = "salesRegisterDto")

	
	ProductTypeDto productTypeToProductTypeDto(ProductType productType);
	ProductType productTypeDtoToProductType(ProductTypeDto productTypeDto);
	
	List<ProductTypeDto> domainToDtoList(List<ProductType> domainList);

}
