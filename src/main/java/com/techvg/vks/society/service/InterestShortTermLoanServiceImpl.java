package com.techvg.vks.society.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.InterestShortTermLoan;
import com.techvg.vks.society.domain.LoanProduct;
import com.techvg.vks.society.mapper.InterestShortTermLoanMapper;
import com.techvg.vks.society.model.InterestShortTermLoanDto;
import com.techvg.vks.society.model.InterestShortTermLoanPageList;
import com.techvg.vks.society.repository.InterestShortTermLoanRepository;
import com.techvg.vks.society.repository.LoanProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterestShortTermLoanServiceImpl implements InterestShortTermLoanService {

	private final LoanProductRepository loanProductRepository;
	private final InterestShortTermLoanMapper interestShortTermLoanMapper;
	private final InterestShortTermLoanRepository interestShortTermLoanRepository;

	@Override
	public InterestShortTermLoanDto addInterestShortTermLoan(
			InterestShortTermLoanDto interestShortTermLoanDto, Authentication authentication) {
		InterestShortTermLoan loanProductOptional= interestShortTermLoanRepository.findByCriteriaAndIsDeleted(interestShortTermLoanDto.getCriteria(), false);
		if (loanProductOptional!=null) {
			throw new AlreadyExitsException("Short Term Loan Interest already exists: " + interestShortTermLoanDto.getCriteria());
		}else {
		InterestShortTermLoan interestShortTermLoan = this.interestShortTermLoanMapper
				.interestShortTermLoangDtoToInterestShortTermLoan(interestShortTermLoanDto);
		interestShortTermLoan = interestShortTermLoanRepository.save(interestShortTermLoan);
		return interestShortTermLoanMapper.interestShortTermLoanToInterestShortTermLoanDto(interestShortTermLoan);
	}
	}
	@Override
	public InterestShortTermLoanDto updateInterestShortTermLoan(Long id,
		InterestShortTermLoanDto interestShortTermLoanDto, Authentication authentication) {
		InterestShortTermLoan interestShortTermLoan=interestShortTermLoanRepository.findById(id).orElseThrow(NotFoundException::new);
		InterestShortTermLoan interestShortTermLoan1=interestShortTermLoanMapper.interestShortTermLoangDtoToInterestShortTermLoan(interestShortTermLoanDto);
		interestShortTermLoan1.setId(interestShortTermLoan.getId());
		return interestShortTermLoanMapper.interestShortTermLoanToInterestShortTermLoanDto(interestShortTermLoanRepository.save(interestShortTermLoan1));
	
	}

	@Override
	public InterestShortTermLoanPageList listAllinterestShortTermLoan(Pageable pageable) {
		Page<InterestShortTermLoan> interestShortTermLoanPage;
		interestShortTermLoanPage = interestShortTermLoanRepository.findByIsDeleted(false,pageable);

		return new InterestShortTermLoanPageList(interestShortTermLoanPage
										.getContent()
										.stream()
										.map(interestShortTermLoanMapper::interestShortTermLoanToInterestShortTermLoanDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(interestShortTermLoanPage.getPageable().getPageNumber(),
													interestShortTermLoanPage.getPageable().getPageSize()),
											interestShortTermLoanPage.getTotalElements());
	}

	@Override
	public InterestShortTermLoanDto deleteById(Long id) {
		log.debug("REST request to Delete  InterestShortTermLoan : {}", id);	
		InterestShortTermLoan interestShortTermLoan=interestShortTermLoanRepository.findById(id).orElseThrow(NotFoundException::new);
		if (interestShortTermLoan != null) {	
			interestShortTermLoan.setIsDeleted(true);
			interestShortTermLoanRepository.save(interestShortTermLoan);
	}
		return interestShortTermLoanMapper.interestShortTermLoanToInterestShortTermLoanDto(interestShortTermLoan);

	}

	@Override
	public InterestShortTermLoanDto getById(Long id) {
		log.debug("REST request to InterestShortTermLoan : {}", id);
		InterestShortTermLoan interestShortTermLoan=interestShortTermLoanRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No InterestShortTermLoan found for Id : " +id));
		return interestShortTermLoanMapper.interestShortTermLoanToInterestShortTermLoanDto(interestShortTermLoan);
	}

}
