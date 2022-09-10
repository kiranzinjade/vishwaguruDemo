package com.techvg.vks.society.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.techvg.vks.society.model.ProductTypeDto;
import com.techvg.vks.society.model.ProfitsAppropriationDto;
import com.techvg.vks.society.domain.ProductType;
import com.techvg.vks.society.domain.ProfitsAppropriation;

@Mapper(componentModel = "spring")
public interface ProfitsAppropriationMapper {
	
	ProfitsAppropriationDto toDto (ProfitsAppropriation profapp);
	ProfitsAppropriation toDomain(ProfitsAppropriationDto profappdto);
	
	List<ProfitsAppropriationDto> toDtoList(List<ProfitsAppropriation> domainList);
}
