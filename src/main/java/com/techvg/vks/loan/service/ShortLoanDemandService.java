package com.techvg.vks.loan.service;

import com.techvg.vks.loan.model.LoanDemandCountDto;
import com.techvg.vks.loan.model.LoanDemandDto;
import com.techvg.vks.loan.model.LoanDemandPageList;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShortLoanDemandService {
	
	LoanDemandDto addLoanDemandDetails(LoanDemandDto loanDemandDto);
	
	LoanDemandDto updateLoanDemandDetails(Long loanDemandId, LoanDemandDto loanDemandDto);
	
	LoanDemandPageList listShortLoanDemand(Pageable pageable);
	
	LoanDemandDto getLoanDemandDetailsById(Long id);
	
	LoanDemandDto deleteLoanDemandDetailsById(Long id);

	List<LoanDemandDto> getSTLoanDemand();

	List<LoanDemandCountDto> getMemberCountForCrop(Integer year);

	LoanDemandPageList getSTLoanDemandList(Pageable pageable, String cropName, Integer year);
}
