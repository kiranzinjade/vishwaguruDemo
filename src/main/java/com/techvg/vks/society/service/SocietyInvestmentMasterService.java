package com.techvg.vks.society.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.techvg.vks.society.domain.SocietyBank;
import com.techvg.vks.society.model.SocietyBankDto;
import com.techvg.vks.society.model.SocietyInvestmentMasterDto;
import com.techvg.vks.society.model.SocietyInvestmentMasterPageList;

@Service
public interface SocietyInvestmentMasterService {

	SocietyInvestmentMasterDto addSocietyInvestment(SocietyInvestmentMasterDto societyInvestmentMasterDto,
			Authentication authentication);

	SocietyInvestmentMasterPageList listSocietyInvestment(Pageable pageable);

	SocietyInvestmentMasterDto deleteSocietyInvestmentById(Long id);

	SocietyInvestmentMasterDto updateSocietyInvestment(Long id,
			SocietyInvestmentMasterDto societyInvestmentMasterDto);

	SocietyInvestmentMasterDto getSocietyInvestmentById(Long id);

	List<SocietyBankDto> getBankDetailsBysocietyInvestmentId();

	List<SocietyInvestmentMasterDto> getSchemeBySocietyBankId(Long societyBankId);

}
