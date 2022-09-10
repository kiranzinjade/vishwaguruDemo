package com.techvg.vks.society.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.techvg.vks.society.model.LoanProductDto;
import com.techvg.vks.society.model.LoanProductList;

public interface LoanProductService {

	LoanProductDto addLoanProduct(LoanProductDto loanProductDto, Authentication authentication);
	
	LoanProductList getAllLoanProducts(Pageable pageable);

	LoanProductDto deleteAllLoanProducts(Long id);

	LoanProductDto updateLoanProduct(Long id, LoanProductDto loanProductDto);

	LoanProductDto readLoanProduct(Long id);

	List <LoanProductDto> listLoanProduct(String loanType);
	
	
	
}
