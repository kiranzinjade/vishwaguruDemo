package com.techvg.vks.loan.service;

import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.model.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public interface LoanDetailsService {

	DisbursementDto addDisbursementDetails(DisbursementDto disbursementDto);

	LoanDetailsDto updateLoanDetails(Long loanId, LoanDetailsDto loanDetailsDto);
	
	LoanDetailsDto updateLoanDetailsforcloser(Long loanId);

	LoanDetailsDto getLoanDetailsById(Long loanId);

	LoanDetails getLoanAccNo(Long loanAccNo);

	ListingPage_PageList listLoanMemberDetails(Pageable pageable);

	ViewLoanDetailsPageList listLoanMembers(Pageable pageable);

	DisbursementDto updateDisbursementDetails(Long disbursementId, DisbursementDto disbursementDto);

	DisbursementDto getDisbursementById(Long disbursementId);

	List<DisbursementDto> getDisbursementListByLoanId(Long loanId);

	LoanInterestDetails calculateLoanInterest(Long loanId,Date installmentDate);

	LoanInterestDetails calculateLoanInterest(LoanDetails loanDetails, Date installmentDate);

	LoanInterestDetails calculateForeclosureCharge(Long loanId,Date installmentDate,String loanType);

	List<LoanDetailsDto> listAccountNoByMemberId(Long memberId);

	List<LoanDetailsDto> listAllAccountNo();

	LoanDetailsDto registerSTLoan(LoanDetailsDto loanDetailsDto);

    List<LoanBriefDto> getSTLoanList(Long memberId);

	List<LoanBriefDto>  getMTLoanList(Long memberId);

	List<LoanBriefDto>  getLTLoanList(Long memberId);

	List<LoanBriefDto>  getActiveLoanList();

	List<LoanDetailsDto> registerAllSTLoansByKmp(Integer slot);

	List<LoanInterestDto> listNpa(Pageable pageable);

	List<LoanDetailsDto> getLoanDetailsByMemberId(Long memberId);

	CropLoanDetailsPageList listCropLoanMembers(Pageable pageable);

	List<AmortizationDto> getAmortizationList(Long loanId);
	
	AmortizationDto scheduleAmortization(LoanDetails loanDetails);

	double getStandardLoanCount();

	double getDefaultLoanCount();

	List<LoanBriefDto> getDefaultLoanList();

	List<LoanDetailsDto> getShortLoanList();

	List<LoanDetailsDto> getMidLongLoanList();
	
}
