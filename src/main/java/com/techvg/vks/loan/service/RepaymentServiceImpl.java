package com.techvg.vks.loan.service;

import com.techvg.vks.accounts.model.VouchersDto;
import com.techvg.vks.accounts.repository.VouchersRepository;
import com.techvg.vks.accounts.service.vouchers.LoanVoucherService;
import com.techvg.vks.config.LoanConstants;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.Amortization;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.domain.Repayment;
import com.techvg.vks.loan.mapper.RepaymentMapper;
import com.techvg.vks.loan.model.LoanInterestDetails;
import com.techvg.vks.loan.model.RepaymentDto;
import com.techvg.vks.loan.repository.AmortizationRepository;
import com.techvg.vks.loan.repository.LoanDetailsRepository;
import com.techvg.vks.loan.repository.RepaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class RepaymentServiceImpl implements RepaymentService{
	
	private final RepaymentRepository repaymentRepository;
	private final RepaymentMapper repaymentMapper;
	private final LoanDetailsRepository loanDetailsRepository;
	private final LoanDetailsService loanDetailsService;
	private final LoanVoucherService loanVoucherService;
	private final VouchersRepository vouchersRepository;
	private final AmortizationRepository amortizationRepository;
	
	private RepaymentDto createRepayment(LoanDetails loan, RepaymentDto repaymentDto) {

		List<Repayment> repaymentList=repaymentRepository.findByLoanDetailsId(loan.getId());
		
		 double closingPrinciple=0.0;
		 double loanAmt= loan.getLoanAmt();
		 double amtPaid=repaymentDto.getInstallmentAmt();
		 double outstandingloanInterest=0.0; 
		 int installmentNo=1;
		 if(repaymentList!=null && repaymentList.size()>0 ) {
						
				for(Repayment repayment1: repaymentList) {
					repaymentDto.setOpeningPrinciple(repayment1.getClosingPrinciple());
					installmentNo = repayment1.getInstallmentNo() + 1;
				}
					closingPrinciple = repaymentDto.getOpeningPrinciple();
		 }else {
				closingPrinciple=loanAmt;
				installmentNo=1;
		 }
		
		LoanInterestDetails loanInterestDetails =loanDetailsService.calculateLoanInterest(loan.getId(),repaymentDto.getInstallmentDate());

		outstandingloanInterest=loanInterestDetails.getCurrentLoanInterest() + loanInterestDetails.getPenaltyAmount() + loanInterestDetails.getSurchargeAmt();

		repaymentDto.setTotalInterest(outstandingloanInterest);
		repaymentDto.setInstallmentNo(installmentNo);
		repaymentDto.setPenalty(loanInterestDetails.getPenaltyAmount());
		repaymentDto.setSurCharge(loanInterestDetails.getSurchargeAmt());

		double principlePaid = 0.0;
		double balanceInterest=0.0;
			if(amtPaid>=outstandingloanInterest) { 
				principlePaid = amtPaid-outstandingloanInterest;
				repaymentDto.setInterestPaid(outstandingloanInterest); 
				repaymentDto.setPrinciplePaid(principlePaid);
				repaymentDto.setOpeningPrinciple(closingPrinciple);
				repaymentDto.setClosingPrinciple(closingPrinciple - principlePaid); 
				repaymentDto.setBalanceInterest(balanceInterest);

			}
			else { 
				repaymentDto.setInterestPaid(amtPaid); 
				repaymentDto.setBalanceInterest(outstandingloanInterest - amtPaid); 
				repaymentDto.setOpeningPrinciple(closingPrinciple);
				repaymentDto.setPrinciplePaid(principlePaid);
				repaymentDto.setClosingPrinciple(closingPrinciple - principlePaid); 
			}
			
		return repaymentDto;
	}

	@Override
	public VouchersDto acceptSTLoanRepayment(RepaymentDto repaymentDto) {
		updateRepayment(repaymentDto);
		return loanVoucherService.updateRepaymentVoucher(repaymentDto.getVouchersDto());
	}

	private RepaymentDto updateRepayment(RepaymentDto repaymentDto){
		Long loanId = repaymentDto.getLoanId();
		LoanDetails loan=loanDetailsRepository.findById(loanId)
				.orElseThrow(() -> new NotFoundException("No Loan details found for Id : " + loanId));
		Repayment repayment = repaymentMapper.repaymentDtoToRepayment(repaymentDto);
		repayment.setLoanDetails(loan);
		repaymentRepository.save(repayment);
		if(loan.getLoanType().equalsIgnoreCase(LoanConstants.MID_TERM_LOAN) || loan.getLoanType().equalsIgnoreCase(LoanConstants.LONG_TERM_LOAN))
			updateAmortization(loan, repayment);
		return repaymentMapper.reapymentToRepaymentDto(repayment);
	}

	@Override
	public RepaymentDto previewSTLoanRepayment(RepaymentDto repaymentDto) {
		Long loanId = repaymentDto.getLoanId();
		LoanDetails loan=loanDetailsRepository.findById(loanId)
				.orElseThrow(() -> new NotFoundException("No Loan details found for Id : " + loanId));

		repaymentDto = createRepayment(loan, repaymentDto);
		VouchersDto vouchersDto = loanVoucherService.createLoanReceiptVoucher(loan,repaymentDto);
		repaymentDto.setVouchersDto(vouchersDto);
		repaymentDto.setVoucherNo(vouchersDto.getVoucherNo());
		return repaymentDto;
	}

	@Override
	public List<RepaymentDto> getLoanRepaymentById(Long loanId) {
		List<Repayment> repaymentlist=repaymentRepository.findByLoanDetailsId(loanId);
		return repaymentMapper.toDtoList(repaymentlist);
	}

	@Override
	public RepaymentDto getLastRepaymentById(Long loanId) {
	Repayment repayment=repaymentRepository.findLastRepaymentByLoanId(loanId);
		return repaymentMapper.reapymentToRepaymentDto(repayment);
	}

	private List<Amortization> getOverDueEmi(LoanDetails loanDetails, String status){
		Set<Amortization> amortizationList = loanDetails.getAmortizations();
		List<Amortization> amortizationOverDueList = new ArrayList<>();
		for(Amortization amortization: amortizationList){
			if(amortization.getPaymentStatus().equalsIgnoreCase(status)){
				amortizationOverDueList.add(amortization);
			}
		}
		return amortizationOverDueList;
	}
	private boolean updateAmortization(LoanDetails loanDetails,Repayment repayment ){
		List<Amortization> overdueList = getOverDueEmi(loanDetails, LoanConstants.OVERDUE);
		List<Amortization> partialOverdueList = getOverDueEmi(loanDetails, LoanConstants.PARTIAL_OVERDUE);

		Date currentDate = new Date();
		double principlePaid = repayment.getPrinciplePaid();
		double interestPaid = repayment.getInterestPaid() - (repayment.getPenalty() + repayment.getSurCharge());

		for(Amortization amortization: partialOverdueList) {
			boolean isPartialOverdue = false;
			if(amortization.getPaymentStatus().equalsIgnoreCase(LoanConstants.PARTIAL_OVERDUE)){
				if(interestPaid > 0) {
					if (amortization.getBalInterestAmt() > 0 && amortization.getBalInterestAmt() <= interestPaid) {
						interestPaid = interestPaid - amortization.getBalInterestAmt();

					} else if (amortization.getBalInterestAmt() > interestPaid) {
						isPartialOverdue = true;
						amortization.setBalInterestAmt(amortization.getBalInterestAmt() - interestPaid);
						interestPaid =0;
					}
				}
				if(principlePaid > 0) {
					if (amortization.getBalPrincipleAmt() <= principlePaid) {
						principlePaid = principlePaid - amortization.getBalPrincipleAmt();

					} else if (amortization.getBalPrincipleAmt() > principlePaid) {
						isPartialOverdue = true;
						amortization.setBalPrincipleAmt(amortization.getBalPrincipleAmt() - principlePaid);
						principlePaid=0;
					}
				}
				if(isPartialOverdue){
					amortization.setPaymentStatus(LoanConstants.PARTIAL_OVERDUE);
				}
				else{
					amortization.setPaymentStatus(LoanConstants.LATE_PAID);
				}
				amortization.setInstallmentDate(currentDate);
				amortizationRepository.save(amortization);
			}

		}
		if(interestPaid > 0 || principlePaid > 0) {
			for (Amortization amortization : overdueList) {
				boolean isPartialOverdue = false;
				if (amortization.getPaymentStatus().equalsIgnoreCase(LoanConstants.OVERDUE)) {
					if(interestPaid > 0) {
						if (amortization.getInterestAmt() <= interestPaid) {
							interestPaid = interestPaid - amortization.getInterestAmt();

						} else if (interestPaid > 0) {
							isPartialOverdue = true;
							amortization.setBalInterestAmt(amortization.getInterestAmt() - interestPaid);
							interestPaid = 0;
						}
					}
					if(principlePaid > 0) {
						if (amortization.getPrincipleEMI() <= principlePaid) {
							principlePaid = principlePaid - amortization.getPrincipleEMI();

						} else if (principlePaid > 0) {
							isPartialOverdue = true;
							amortization.setBalPrincipleAmt(amortization.getPrincipleEMI() - principlePaid);
							principlePaid = 0;
						}
					}
					if (isPartialOverdue) {
						amortization.setPaymentStatus(LoanConstants.PARTIAL_OVERDUE);
					} else {
						amortization.setPaymentStatus(LoanConstants.LATE_PAID);
					}
					amortization.setInstallmentDate(currentDate);
					amortizationRepository.save(amortization);
				}
			}
		}
		if(interestPaid > 0 || principlePaid > 0) {
			Amortization amortizationDue = amortizationRepository.findByLoanIdAndStatus( loanDetails.getId() , LoanConstants.DUE);
			boolean isPartial = false;
			if(interestPaid > 0 ){
				if(amortizationDue.getInterestAmt() >= interestPaid){
					interestPaid = interestPaid - amortizationDue.getInterestAmt();
				}else{
					isPartial = true;
					amortizationDue.setBalInterestAmt(amortizationDue.getInterestAmt() - interestPaid);
				}
			}
			if(principlePaid > 0){
				if(amortizationDue.getPrincipleEMI() >= principlePaid){
					principlePaid = principlePaid - amortizationDue.getInterestAmt();
				}else{
					isPartial = true;
					amortizationDue.setBalPrincipleAmt(amortizationDue.getPrincipleEMI() - principlePaid);
				}
			}
			if (isPartial) {
				amortizationDue.setPaymentStatus(LoanConstants.PARTIAL_OVERDUE);
			} else {
				amortizationDue.setPaymentStatus(LoanConstants.PAID);
			}
			amortizationDue.setInstallmentDate(currentDate);
			amortizationRepository.save(amortizationDue);
		}
		return true;
	}

}


