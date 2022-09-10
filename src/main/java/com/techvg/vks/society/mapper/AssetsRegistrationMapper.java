package com.techvg.vks.society.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.techvg.vks.society.domain.AssetsRegistration;
import com.techvg.vks.society.model.AssetsRegistrationDto;


@Mapper(componentModel = "spring")

public interface AssetsRegistrationMapper {
	@Mapping( source = "assetsRegistration.assets.assetsName", target = "assetsName")
	@Mapping( source = "assetsRegistration.assets.assetsType", target = "assetsType")
	@Mapping( source = "assetsRegistration.assets.catagory", target = "catagory")

	AssetsRegistrationDto assetsRegistrationToAssetsRegistrationDto(AssetsRegistration assetsRegistration);

    //@Mapping( target = "vouchersDto", ignore = true )
	AssetsRegistration assetsRegistrationDtoToAssetsRegistration(AssetsRegistrationDto assetsRegistrationDto);
	
//    @Mapping( source = "assets.assets.name", target = "voucherTypeDesc")
}
