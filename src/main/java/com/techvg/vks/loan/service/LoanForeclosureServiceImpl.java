package com.techvg.vks.loan.service;


import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.techvg.vks.common.DateConverter;
import com.techvg.vks.config.LoanConstants;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.domain.Repayment;
import com.techvg.vks.loan.mapper.LoanDetailsMapper;
import com.techvg.vks.loan.mapper.RepaymentMapper;
import com.techvg.vks.loan.model.LoanDetailsDto;
import com.techvg.vks.loan.model.LoanInterestDetails;
import com.techvg.vks.loan.model.RepaymentDto;
import com.techvg.vks.loan.repository.LoanDetailsRepository;
import com.techvg.vks.loan.repository.RepaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanForeclosureServiceImpl implements LoanForeclosureService {
	
	private final LoanDetailsRepository loanDetailsRepository;
	private final LoanDetailsMapper loanDetailsMapper;
	private final LoanDetailsService loanDetailsService;
	private final RepaymentRepository repaymentRepository;
	private final RepaymentMapper repaymentMapper;
	
	int installmentNo=1;
	
	@Override
	public LoanInterestDetails getForeclosureAmtShortLoan(Long loanId, String foreclosureDate) {
		loanDetailsRepository.findById(loanId)
				.orElseThrow(() -> new NotFoundException("No Loan details found for Id : " + loanId));

		double foreClosureAmt =0.0;
		Date foreclosureDate1=DateConverter.getDate(foreclosureDate);
		LoanInterestDetails loanInterestDetails=loanDetailsService.calculateLoanInterest(loanId,foreclosureDate1);
		
	    foreClosureAmt = loanInterestDetails.getTotalOutstandingPrincipal() + loanInterestDetails.getCurrentLoanInterest();
	    loanInterestDetails.setForeclosureChargeAmt(loanInterestDetails.getCurrentLoanInterest());
	    loanInterestDetails.setTotalPaymentAmt(foreClosureAmt);
		return loanInterestDetails;		

	}

	@Override
	public LoanDetailsDto forecloseShortMidLongLoan(Long loanId, LoanInterestDetails loanInterestDetails) {
		LoanDetails loanDetails=loanDetailsRepository.findById(loanId)
				.orElseThrow(() -> new NotFoundException("No Loan details found for Id : " + loanId));
	
		List<Repayment> repaymentList=repaymentRepository.findByLoanDetailsId(loanId);

		if(repaymentList!=null && repaymentList.size()>0 ) {
			repaymentList.forEach(repayment1->{
					installmentNo = repayment1.getInstallmentNo();
					});
			
				}else {
					installmentNo=1;
				}
		
		RepaymentDto repaymentDto = RepaymentDto.builder()
			                           	.closingPrinciple(0.0)
				                        .installmentDate(new Date())
			                         	.installmentAmt(loanInterestDetails.getTotalPaymentAmt())
				                        .interestPaid(loanInterestDetails.getCurrentLoanInterest())
		                        		.principlePaid(loanInterestDetails.getTotalOutstandingPrincipal())
			                        	.balanceInterest(loanInterestDetails.getTotalLoanInterest() - loanInterestDetails.getCurrentLoanInterest())
				                        .openingPrinciple(loanInterestDetails.getTotalOutstandingPrincipal())
				                        .totalInterest(loanInterestDetails.getTotalLoanInterest())
				                        .penalty(loanInterestDetails.getPenaltyAmount())
				                        .surCharge(loanInterestDetails.getForeclosureSurchargeAmt())//
				                        .installmentNo(installmentNo)
				                        .foreClosureCharge(loanInterestDetails.getForeclosureChargeAmt())//
				                        .isDeleted(false)
				                        .build();
		

		Repayment repayment=repaymentMapper.repaymentDtoToRepayment(repaymentDto);
		repayment.setLoanDetails(loanDetails);
		repayment = repaymentRepository.save(repayment);
		
		// set mlloan no -> null in land details for that loan 
				//if(!loanDetails.getLoanType().equals(LoanConstants.SHORT_TERM_LOAN)) {
					loanDetails.getMember().getLand().forEach(action->{
						
						if(action.getMLLoanNo() !=null && action.getMLLoanNo().equals(loanId)){
						action.setMLLoanNo(null);
					}
					});
				//}
		
		loanDetails.setLoanCloserDate(new Date());
		loanDetails.setLoanStatus(LoanConstants.LOAN_CLOSE);
		return loanDetailsMapper.loanDetailsToLoanDetailsDto(loanDetailsRepository.save(loanDetails));
		 
	}

	@Override
	public LoanInterestDetails getForeclosureAmtMidLongLoan(Long loanId, String foreclosureDate) {
		LoanDetails loanDetails=loanDetailsRepository.findById(loanId)
				.orElseThrow(() -> new NotFoundException("No Loan details found for Id : " + loanId));

		double foreClosureAmt =0.0;
		Date foreclosureDate1=DateConverter.getDate(foreclosureDate);

		LoanInterestDetails loanInterestDetails1=loanDetailsService.calculateForeclosureCharge(loanId,new Date(),loanDetails.getLoanType());
  	    foreClosureAmt = loanInterestDetails1.getTotalOutstandingPrincipal() + loanInterestDetails1.getCurrentLoanInterest() + loanInterestDetails1.getPenaltyAmount() + loanInterestDetails1.getSurchargeAmt() + loanInterestDetails1.getForeclosureChargeAmt() +loanInterestDetails1.getForeclosureSurchargeAmt();
	    loanInterestDetails1.setTotalPaymentAmt(foreClosureAmt);

	    return loanInterestDetails1;
	}

}
