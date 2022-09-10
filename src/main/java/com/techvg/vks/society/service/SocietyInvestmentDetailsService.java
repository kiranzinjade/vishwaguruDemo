package com.techvg.vks.society.service;

import com.techvg.vks.society.domain.SocietyInvestment;
import com.techvg.vks.society.model.SocietyInvestmentDetailsDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SocietyInvestmentDetailsService {

    SocietyInvestmentDetailsDto addSocietyInvestmentDetails(SocietyInvestmentDetailsDto societyInvestmentDetailsDto,
                                                     Authentication authentication);

    SocietyInvestmentDetailsDto deleteSocietyInvestmentDetailsById(Long societyInvestmentDetailsId);

    SocietyInvestmentDetailsDto updateSocietyInvestmentDetails(Long societyInvestmentDetailsId, SocietyInvestmentDetailsDto societyInvestmentDetailsDto);

    SocietyInvestmentDetailsDto getSocietyInvestmentDetailsById(Long societyInvestmentDetailsId);

    List<SocietyInvestmentDetailsDto> listSocietyInvestmentDetails();

    boolean addEntryToInvestmentDetails(SocietyInvestment societyInvestment);

	List<SocietyInvestmentDetailsDto> getSocietyInvestmentDetailsByInvestmentId(Long societyInvestmentId);
}
