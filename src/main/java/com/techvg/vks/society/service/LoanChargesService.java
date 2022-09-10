package com.techvg.vks.society.service;

import com.techvg.vks.society.model.LoanChargesDto;
import com.techvg.vks.society.model.LoanChargesPageList;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
public interface LoanChargesService {
	
	LoanChargesDto addLoanCharges(LoanChargesDto loanChargesDto, Authentication authentication);
	LoanChargesPageList listLoanCharges(Pageable pageable);
	LoanChargesDto deleteLoanChargesById(Long id);
	LoanChargesDto updateLoanCharges(Long id, LoanChargesDto loanChargesDto);
	LoanChargesDto getLoanChargesById(Long id);
}
