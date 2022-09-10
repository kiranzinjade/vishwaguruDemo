package com.techvg.vks.society.service;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.LoanCharges;
import com.techvg.vks.society.mapper.LoanChargesMapper;
import com.techvg.vks.society.model.LoanChargesDto;
import com.techvg.vks.society.model.LoanChargesPageList;
import com.techvg.vks.society.repository.LoanChargesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j


public class LoanChargesServiceImpl implements LoanChargesService {
	
	private final LoanChargesMapper loanChargesMapper;
	private final LoanChargesRepository loanChargesRepository;

	@Override
	public LoanChargesDto addLoanCharges(LoanChargesDto loanChargesDto, Authentication authentication) {
		log.debug("REST request to save Prerequisite : {}", loanChargesDto);
		return loanChargesMapper.loanChargesToLoanChargesDto(loanChargesRepository.save(loanChargesMapper.loanChargesDtoToLoanCharges(loanChargesDto)));
	}

	
	@Override
	public LoanChargesPageList listLoanCharges(Pageable pageable) {
		log.debug("Request to get Prerequisite : {}");

		Page<LoanCharges> LoanChargesPage;
		LoanChargesPage = loanChargesRepository.findByisDeleted(pageable,false);
       
		
		return new LoanChargesPageList(LoanChargesPage
										.getContent()
										.stream()
										.map(loanChargesMapper::loanChargesToLoanChargesDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(LoanChargesPage.getPageable().getPageNumber(),
													LoanChargesPage.getPageable().getPageSize()),
											LoanChargesPage.getTotalElements());

	}

	@Override
	public LoanChargesDto deleteLoanChargesById(Long id) {
		LoanCharges preClosure = loanChargesRepository.findById(id).orElseThrow(NotFoundException::new);
		if (preClosure != null) {
			preClosure.setIsDeleted(true);
			loanChargesRepository.save(preClosure);
		}
		return loanChargesMapper.loanChargesToLoanChargesDto(preClosure);
	}

	@Override
	public LoanChargesDto updateLoanCharges(Long id, LoanChargesDto loanChargesDto) {
		LoanCharges loanCharges = loanChargesRepository.findById(id).orElseThrow(NotFoundException::new);
		loanChargesDto.setId(loanCharges.getId());
		LoanCharges loanCharges1 = loanChargesMapper.loanChargesDtoToLoanCharges(loanChargesDto);
		return loanChargesMapper.loanChargesToLoanChargesDto(loanChargesRepository.save(loanCharges1));
	}

	@Override
	public LoanChargesDto getLoanChargesById(Long id) {
		log.debug("REST request to get Prerequisite : {}", id);
		LoanCharges loanCharges = loanChargesRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No Npa Setting found for Id : " +id));

		return loanChargesMapper.loanChargesToLoanChargesDto(loanCharges);
	}
}
