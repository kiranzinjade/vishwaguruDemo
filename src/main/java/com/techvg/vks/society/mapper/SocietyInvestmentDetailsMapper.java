package com.techvg.vks.society.mapper;

import com.techvg.vks.society.domain.SocietyInvestmentDetails;
import com.techvg.vks.society.model.SocietyInvestmentDetailsDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SocietyInvestmentDetailsMapper {

    SocietyInvestmentDetails toDomain(SocietyInvestmentDetailsDto dto);
    SocietyInvestmentDetailsDto toDto(SocietyInvestmentDetails domain);

    List<SocietyInvestmentDetailsDto> toDtoList(List<SocietyInvestmentDetails> dtoList);
}
