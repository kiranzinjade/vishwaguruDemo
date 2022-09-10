package com.techvg.vks.society.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.techvg.vks.society.model.DepositTypeDto;
import com.techvg.vks.society.model.DepositTypePageList;
import com.techvg.vks.society.model.SocietyBankDto;

public interface DepositTypeService {
	DepositTypeDto addDepositType(DepositTypeDto depositTypeDto, Authentication authentication);
	DepositTypePageList listDepositType(Pageable pageable);
	DepositTypeDto deleteDepositTypeById(Long id);
	DepositTypeDto updateDepositType(Long id, DepositTypeDto depositTypeDto);
	DepositTypeDto getDepositTypeById(Long id);
	List<DepositTypeDto> listDepositType();


}
