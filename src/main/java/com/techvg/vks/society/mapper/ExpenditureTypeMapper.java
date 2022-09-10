package com.techvg.vks.society.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import com.techvg.vks.society.domain.ExpenditureType;
import com.techvg.vks.society.model.ExpenditureTypeDto;

@Mapper(componentModel="spring")
public interface ExpenditureTypeMapper {
	
	ExpenditureTypeDto expenditureTypeToExpenditureTypeDto(ExpenditureType expenditureType);
	ExpenditureType expenditureTypeDtoToExpenditureType(ExpenditureTypeDto expenditureTypeDto);
	
    List<ExpenditureTypeDto> domainToDtoList(List<ExpenditureType> domainList);

}
