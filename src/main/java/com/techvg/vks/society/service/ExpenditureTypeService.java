package com.techvg.vks.society.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.techvg.vks.society.model.ExpenditureTypeDto;
import com.techvg.vks.society.model.ExpenditureTypePageList;

@Service
public interface ExpenditureTypeService {

	List<ExpenditureTypeDto> listExpenditure();

	ExpenditureTypeDto addExpenditureType(ExpenditureTypeDto expenditureTypeDto, Authentication authentication);
	ExpenditureTypePageList listExpenditureType(Pageable pageable);
	ExpenditureTypeDto deleteExpenditureTypeById(Long id);
	ExpenditureTypeDto updateExpenditureType(Long id, ExpenditureTypeDto expenditureTypeDto);
	ExpenditureTypeDto getExpenditureTypeById(Long id);

}
