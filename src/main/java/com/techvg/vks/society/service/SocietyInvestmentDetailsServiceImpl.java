package com.techvg.vks.society.service;

import com.techvg.vks.config.DepositConstants;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.SocietyInvestment;
import com.techvg.vks.society.domain.SocietyInvestmentDetails;
import com.techvg.vks.society.mapper.SocietyInvestmentDetailsMapper;
import com.techvg.vks.society.model.SocietyInvestmentDetailsDto;
import com.techvg.vks.society.repository.SocietyInvestmentDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SocietyInvestmentDetailsServiceImpl implements SocietyInvestmentDetailsService {
    private final SocietyInvestmentDetailsRepository socInvestDetailsRepository;
    private final SocietyInvestmentDetailsMapper socInvestDetailsMapper;

    public SocietyInvestmentDetailsDto addSocietyInvestmentDetails(SocietyInvestmentDetailsDto societyInvestmentDetailsDto, Authentication authentication) {
        return socInvestDetailsMapper.toDto(socInvestDetailsRepository.saveAndFlush(socInvestDetailsMapper.toDomain(societyInvestmentDetailsDto)));
    }

    @Override
    public SocietyInvestmentDetailsDto deleteSocietyInvestmentDetailsById(Long societyInvestmentDetailsId) {
        SocietyInvestmentDetails societyInvestmentDetails = socInvestDetailsRepository.findById(societyInvestmentDetailsId).orElseThrow(
                () -> new NotFoundException("No Society Investment Setting found for Id : " +societyInvestmentDetailsId));
        if (societyInvestmentDetails != null) {
            societyInvestmentDetails.setIsDeleted(true);
            socInvestDetailsRepository.save(societyInvestmentDetails);
        }
        return socInvestDetailsMapper.toDto(societyInvestmentDetails);
    }

    @Override
    public SocietyInvestmentDetailsDto updateSocietyInvestmentDetails(Long societyInvestmentDetailsId, SocietyInvestmentDetailsDto societyInvestmentDetailsDto) {
        SocietyInvestmentDetails societyInvestmentDetails = socInvestDetailsRepository.findById(societyInvestmentDetailsId).orElseThrow(
                () -> new NotFoundException("No Society Investment Setting found for Id : " +societyInvestmentDetailsId));
        societyInvestmentDetailsDto.setId(societyInvestmentDetails.getId());
        SocietyInvestmentDetails societyInvestment1 = socInvestDetailsMapper.toDomain(societyInvestmentDetailsDto);
        return socInvestDetailsMapper.toDto(socInvestDetailsRepository.save(societyInvestment1));

    }

    @Override
    public SocietyInvestmentDetailsDto getSocietyInvestmentDetailsById(Long societyInvestmentDetailsId) {

        SocietyInvestmentDetails societyInvestmentDetails = socInvestDetailsRepository.findById(societyInvestmentDetailsId).orElseThrow(
                () -> new NotFoundException("No Society Investment Setting found for Id : " +societyInvestmentDetailsId));

        return socInvestDetailsMapper.toDto(societyInvestmentDetails);
    }

    @Override
    public List<SocietyInvestmentDetailsDto> listSocietyInvestmentDetails() {
        return socInvestDetailsMapper.toDtoList(socInvestDetailsRepository.findAll());
    }

    @Override
    public boolean addEntryToInvestmentDetails(SocietyInvestment societyInvestment) {
        if(societyInvestment != null){
            SocietyInvestmentDetails societyInvestmentDetails = new SocietyInvestmentDetails();
            societyInvestmentDetails.setDepositDate(societyInvestment.getDepositDate());
            societyInvestmentDetails.setDebitAmount(societyInvestment.getDepositAmount());
            societyInvestmentDetails.setBalanceAmount(societyInvestment.getDepositAmount());
            societyInvestmentDetails.setCreditAmount(0.0);
            societyInvestmentDetails.setInterestAmount(0.0);
            societyInvestmentDetails.setParticulars(DepositConstants.DEPOSIT_INITIAL);
            societyInvestmentDetails.setSocietyInvestment(societyInvestment);
            socInvestDetailsRepository.saveAndFlush(societyInvestmentDetails);
            return true;
        }
        return false;
    }

	@Override
	public List<SocietyInvestmentDetailsDto> getSocietyInvestmentDetailsByInvestmentId(Long societyInvestmentId) {
		List< SocietyInvestmentDetails> dtoList = socInvestDetailsRepository.findBySocietyInvestmentId(societyInvestmentId);

	        return socInvestDetailsMapper.toDtoList(dtoList);
	}
}
