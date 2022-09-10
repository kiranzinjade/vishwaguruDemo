package com.techvg.vks.society.service;

import com.techvg.vks.common.DateConverter;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.SocietyInvestment;
import com.techvg.vks.society.domain.SocietyInvestmentMaster;
import com.techvg.vks.society.mapper.SocietyInvestmentMapper;
import com.techvg.vks.society.model.SocietyInvestmentDto;
import com.techvg.vks.society.model.SocietyInvestmentPageList;
import com.techvg.vks.society.repository.SocietyInvestmentMasterRepository;
import com.techvg.vks.society.repository.SocietyInvestmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocietyInvestmentServiceImpl implements SocietyInvestmentService {

	private final SocietyInvestmentMapper societyInvestmentMapper;
	private final SocietyInvestmentRepository societyInvestmentRepository;
	private final SocietyInvestmentMasterRepository   societyInvestmentMasterRepository;
	private final SocietyInvestmentDetailsService socInvestDetailsService;

	@Override
	public SocietyInvestmentDto addSocietyInvestment(SocietyInvestmentDto societyInvestmentDto, Authentication authentication) {
		log.debug("REST request to save societyInvestment : {}", societyInvestmentDto);
		SocietyInvestment societyInvestment= societyInvestmentMapper.societyInvestmentDtoToSocietyInvestment(societyInvestmentDto);
		SocietyInvestmentMaster societyInvestmentMaster=societyInvestmentMasterRepository.findById(societyInvestmentDto.getSocietyInvestmentId()).orElseThrow(
				() -> new NotFoundException("No Society Investment Setting found for Id : " + societyInvestmentDto.getSocietyInvestmentId()));
		Integer duration=societyInvestmentMaster.getPeriod();
		societyInvestment.setSocietyInvestmentMaster(societyInvestmentMaster);
		Date maturityDate=societyInvestment.getDepositDate();
		 
	   Date d1= DateConverter.incrementDateByDays(duration, maturityDate);
		societyInvestment.setMaturityDate(d1);

	    Double depositAmt = societyInvestment.getDepositAmount();
		Double roi = societyInvestmentMaster.getInterest();
		Integer durationDays = societyInvestmentMaster.getPeriod();
		Double perdayinterest = (depositAmt * roi / 100 / 365);
		Double maturityInterestAmt = perdayinterest * durationDays;
		Double maturityAmt = depositAmt + maturityInterestAmt;
		societyInvestment.setMaturityAmount(maturityAmt);
		societyInvestment.setInterestAmount(maturityInterestAmt);
		societyInvestment = societyInvestmentRepository.save(societyInvestment);
		socInvestDetailsService.addEntryToInvestmentDetails(societyInvestment);
	    return societyInvestmentMapper.societyInvestmentToSocietyInvestmentDto(societyInvestment);
	    
	}

	@Override
	public SocietyInvestmentDto deleteSocietyInvestmentById(Long id) {
		SocietyInvestment societyInvestment = societyInvestmentRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No Society Investment Setting found for Id : " +id));
		if (societyInvestment != null) {
			societyInvestment.setIsDeleted(true);
			societyInvestmentRepository.save(societyInvestment);
		}
	return societyInvestmentMapper.societyInvestmentToSocietyInvestmentDto(societyInvestment);
	
	}

	@Override
	public SocietyInvestmentDto updateSocietyInvestment(Long id,
															   SocietyInvestmentDto societyInvestmentDto) {
		SocietyInvestment societyInvestment = societyInvestmentRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No Society Investment Setting found for Id : " +id));
		societyInvestmentDto.setId(societyInvestment.getId());
		SocietyInvestment societyInvestment1 = societyInvestmentMapper.societyInvestmentDtoToSocietyInvestment(societyInvestmentDto);
		return societyInvestmentMapper.societyInvestmentToSocietyInvestmentDto(societyInvestmentRepository.save(societyInvestment1));
	
	}

	@Override
	public SocietyInvestmentDto getSocietyInvestmentById(Long id) {
		log.debug("REST request to get Society Investment : {}", id);
		SocietyInvestment societyInvestment = societyInvestmentRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No Society Investment Setting found for Id : " +id));

		return societyInvestmentMapper.societyInvestmentToSocietyInvestmentDto(societyInvestment);
	
	}

	@Override
	public SocietyInvestmentPageList listSocietyInvestments(Pageable pageable) {
    log.debug("REST request to get Society Investment : {}");
		
		Page<SocietyInvestment> societyInvestmentPage;
		societyInvestmentPage = societyInvestmentRepository.findByisDeleted(pageable,false);
       
		
		return new SocietyInvestmentPageList(societyInvestmentPage
										.getContent()
										.stream()
										.map(societyInvestmentMapper::societyInvestmentToSocietyInvestmentDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(societyInvestmentPage.getPageable().getPageNumber(),
													societyInvestmentPage.getPageable().getPageSize()),
											societyInvestmentPage.getTotalElements());
	}

}
