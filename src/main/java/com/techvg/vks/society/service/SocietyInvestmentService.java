package com.techvg.vks.society.service;

import com.techvg.vks.society.model.SocietyInvestmentDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.techvg.vks.society.model.SocietyInvestmentPageList;

@Service
public interface SocietyInvestmentService {

	SocietyInvestmentDto addSocietyInvestment(SocietyInvestmentDto societyInvestmentDto,
                                                     Authentication authentication);

	SocietyInvestmentDto deleteSocietyInvestmentById(Long id);

	SocietyInvestmentDto updateSocietyInvestment(Long id, SocietyInvestmentDto societyInvestmentDto);

	SocietyInvestmentDto getSocietyInvestmentById(Long id);

	SocietyInvestmentPageList listSocietyInvestments(Pageable pageable);

}
