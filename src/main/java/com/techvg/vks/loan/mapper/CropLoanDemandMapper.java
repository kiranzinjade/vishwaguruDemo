package com.techvg.vks.loan.mapper;

import com.techvg.vks.loan.domain.CropLoanDemand;
import com.techvg.vks.loan.model.CropLoanDemandDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CropLoanDemandMapper {

    CropLoanDemand toDomain(CropLoanDemandDto dto);

    @Mapping(source = "domain.crop.id", target = "cropRegistrationId")
    @Mapping(source = "domain.crop.cropName", target = "cropName")
    @Mapping(source = "domain.crop.season", target = "season")
    CropLoanDemandDto toDto(CropLoanDemand domain);

    List<CropLoanDemandDto> toDtoList(List<CropLoanDemand> domain);
}
