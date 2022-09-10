package com.techvg.vks.society.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.techvg.vks.society.domain.ExpenditureRegister;
import com.techvg.vks.society.model.ExpenditureRegisterDto;

@Mapper(componentModel = "spring")

public interface ExpenditureRegisterMapper {
	
	ExpenditureRegisterDto toExpenditureRegisterDto(ExpenditureRegister expenditureRegister);

	ExpenditureRegister toExpenditureRegister(ExpenditureRegisterDto expenditureRegisterDto);
	
	List<ExpenditureRegisterDto> domainToDtoList(List<ExpenditureRegister> domainList);
}
