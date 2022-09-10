package com.techvg.vks.loan.service;

import com.techvg.vks.loan.model.LoanInterestDetails;
import com.techvg.vks.loan.model.ShortTermSubsidyDto;
import com.techvg.vks.loan.model.ShortTermSubsidyPageList;
import com.techvg.vks.loan.model.SubsidyDtoPageList;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ShortTermSubsidyervice {

	ShortTermSubsidyDto addSubsidyDetails(Long loanId, LoanInterestDetails loanInterestDetails);

	ShortTermSubsidyDto updateSubsidyDetails(Long subsidyId, ShortTermSubsidyDto shortTermSubsidyDto);
	
	ShortTermSubsidyPageList list(Pageable pageable);

	SubsidyDtoPageList listSubsidy(Pageable pageable, Long loanId);
	
}
