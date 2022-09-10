package com.techvg.vks.society.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import com.techvg.vks.society.model.ProfitsAppropriationDto;
import com.techvg.vks.society.model.ProfitsAppropriationPageList;

@Service
public interface ProfitsAppropriationService {

	ProfitsAppropriationDto addProfitApp (ProfitsAppropriationDto profitAppDto , Authentication authentication);

	ProfitsAppropriationPageList listProfitApp(Pageable pageable);

	ProfitsAppropriationDto deleteProfitAppById(Long id);

	ProfitsAppropriationDto updateProfitApp(Long id, ProfitsAppropriationDto profitAppDto);

	ProfitsAppropriationDto getProfitAppById(Long id);
	
	//List<ProfitsAppropriationDto> listAllProfitApp();

}
