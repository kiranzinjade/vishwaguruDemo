package com.techvg.vks.loan.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.techvg.vks.loan.model.LoanWatapDto;
import com.techvg.vks.loan.model.LoanWatapPageList;

public interface LoanWatapService {

	Boolean addLoanWatap(List<Long> loanWatapId);

	LoanWatapDto updateLoanWatap(Long loanWatapId, LoanWatapDto loanWatapDto);

	LoanWatapDto getLoanWatapId(Long id);

	LoanWatapPageList listAllLoanWatap(Pageable pageable);

	LoanWatapDto deleteLoanWatapById(Long id);

	List<LoanWatapDto> listLoanWatapBySlot(int slot);

	List<LoanWatapDto> allSlot();

	Boolean approveLoanWatap(int slot);
	
	

}
