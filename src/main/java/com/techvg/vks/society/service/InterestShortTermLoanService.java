package com.techvg.vks.society.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.techvg.vks.society.model.InterestShortTermLoanDto;
import com.techvg.vks.society.model.InterestShortTermLoanPageList;

public interface InterestShortTermLoanService {

	InterestShortTermLoanDto addInterestShortTermLoan(InterestShortTermLoanDto interestShortTermLoanDto,
			Authentication authentication);

	InterestShortTermLoanDto updateInterestShortTermLoan(Long id,
			InterestShortTermLoanDto interestShortTermLoanDto, Authentication authentication);

	InterestShortTermLoanPageList listAllinterestShortTermLoan(Pageable pageable);

	InterestShortTermLoanDto deleteById(Long id);

	InterestShortTermLoanDto getById(Long id);

}
