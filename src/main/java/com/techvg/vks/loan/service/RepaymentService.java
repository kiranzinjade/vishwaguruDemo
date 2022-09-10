package com.techvg.vks.loan.service;

import com.techvg.vks.accounts.model.VouchersDto;
import com.techvg.vks.loan.model.RepaymentDto;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface RepaymentService {

    VouchersDto acceptSTLoanRepayment(RepaymentDto repaymentDto);

    RepaymentDto previewSTLoanRepayment(RepaymentDto repaymentDto);

    List<RepaymentDto> getLoanRepaymentById(Long loanId);

	RepaymentDto getLastRepaymentById(Long loanId);

	
}
