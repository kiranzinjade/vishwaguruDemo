package com.techvg.vks.loan.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.techvg.vks.config.LoanConstants;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.domain.Repayment;
import com.techvg.vks.loan.domain.ShortTermSubsidy;
import com.techvg.vks.loan.mapper.LoanDetailsMapper;
import com.techvg.vks.loan.mapper.RepaymentMapper;
import com.techvg.vks.loan.model.LoanDetailsDto;
import com.techvg.vks.loan.model.LoanInterestDetails;
import com.techvg.vks.loan.model.RepaymentDto;
import com.techvg.vks.loan.model.LoanClosureDto;
import com.techvg.vks.loan.repository.LoanDetailsRepository;
import com.techvg.vks.loan.repository.RepaymentRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanClosureServiceImpl implements LoanClosureService{
	
	private final LoanDetailsRepository loanDetailsRepository;
	private final LoanDetailsService loanDetailsService;
	private final RepaymentRepository repaymentRepository;
	private final RepaymentMapper repaymentMapper;
	private final LoanDetailsMapper loanDetailsMapper;
	private final ShortTermSubsidyervice  shortTermSubsidyervice;
	int installmentNo=1;
	

	@Override
	public LoanDetailsDto closeLoan(LoanClosureDto loanClosureDto) {

		Long id=loanClosureDto.getLoanId();
		
		LoanDetails loanDetails=loanDetailsRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No Loan details found for Id : " + id));
		LoanInterestDetails loanInterestDetails=loanDetailsService.calculateLoanInterest(id,new Date());
		
		List<Repayment> repaymentList=repaymentRepository.findByLoanDetailsId(id);
		
				
			
			if(repaymentList!=null && repaymentList.size()>0 ) {
				repaymentList.forEach(repayment1->{
						installmentNo = repayment1.getInstallmentNo() + 1;
						});
				
					}else {
						installmentNo=1;
					}
		
		RepaymentDto repaymentDto = RepaymentDto.builder()
			                           	.closingPrinciple(loanInterestDetails.getTotalOutstandingPrincipal() - loanClosureDto.getOutStandingPrinciple())
				                        .installmentDate(new Date())
			                         	.installmentAmt(loanClosureDto.getTotalClosureAmount())
				                        .interestPaid(loanClosureDto.getOutStandingInterest())
		                        		.principlePaid(loanClosureDto.getOutStandingPrinciple())
			                        	.balanceInterest(loanInterestDetails.getCurrentLoanInterest() - loanClosureDto.getOutStandingInterest())
				                        .openingPrinciple(loanInterestDetails.getTotalOutstandingPrincipal())
				                        .totalInterest(loanInterestDetails.getTotalLoanInterest())
				                        .penalty(loanClosureDto.getPenalty())
				                        .surCharge(loanClosureDto.getSurCharge())
				                        .installmentNo(installmentNo)
				                        .isDeleted(false)
				                        .build();
		
		
		Repayment repayment=repaymentMapper.repaymentDtoToRepayment(repaymentDto);
		repayment.setLoanDetails(loanDetails);
		repaymentRepository.save(repayment);
	
		if(loanClosureDto.isSettlement()) {
			loanDetails.setLoanStatus(LoanConstants.LOAN_SETTLED);
		}
		else {
			loanDetails.setLoanStatus(LoanConstants.LOAN_CLOSE);
		}
		loanDetails.setLoanCloserDate(new Date());
		
		// set mlloan no -> null in land details for that loan 
		//if(!loanDetails.getLoanType().equals(LoanConstants.SHORT_TERM_LOAN)) {
			loanDetails.getMember().getLand().forEach(action->{
				
				if(action.getMLLoanNo() !=null && action.getMLLoanNo().equals(id)){
				action.setMLLoanNo(null);
			}
			});
		//}
		
		//add loan Subsidy details
			if(loanDetails.getLoanType().equals(LoanConstants.SHORT_TERM_LOAN)) {
				if(loanDetails.getLoanCloserDate().compareTo(loanDetails.getLoanPlannedClosureDate())<=0) {
					LoanInterestDetails interestDetails=loanDetailsService.calculateLoanInterest(loanDetails, loanDetails.getLoanCloserDate());
					shortTermSubsidyervice.addSubsidyDetails(loanDetails.getId(), interestDetails);
				}
			}
			
			
		return loanDetailsMapper.loanDetailsToLoanDetailsDto(loanDetailsRepository.save(loanDetails));
		
	}

	@Override
	public LoanClosureDto getLoanClosureDetails(Long loanId) {
		
		LoanDetails loanDetails=loanDetailsRepository.findById(loanId)
				.orElseThrow(() -> new NotFoundException("No Loan details found for Id : " + loanId));

		LoanClosureDto loanClosureDto= new LoanClosureDto();
		LoanInterestDetails loanInterestDetails=loanDetailsService.calculateLoanInterest(loanId,new Date());

		double closureAmt = loanInterestDetails.getTotalOutstandingPrincipal() + loanInterestDetails.getCurrentLoanInterest();
		  if(loanDetails.getLoanPlannedClosureDate().compareTo(new Date()) <= 0) {
			  
			  closureAmt = closureAmt + loanInterestDetails.getPenaltyAmount() + loanInterestDetails.getSurchargeAmt();
		  }
		
		  loanClosureDto.setOutStandingPrinciple(loanInterestDetails.getTotalOutstandingPrincipal());
		  loanClosureDto.setOutStandingInterest(loanInterestDetails.getCurrentLoanInterest());
		  loanClosureDto.setPenalty(loanInterestDetails.getPenaltyAmount());
		  loanClosureDto.setSurCharge(loanInterestDetails.getSurchargeAmt());
		  loanClosureDto.setTotalClosureAmount(closureAmt);
		  loanClosureDto.setSettlement(false);
		  loanClosureDto.setLoanId(loanInterestDetails.getLoanId());
		
		return loanClosureDto;
	}

}
