package com.techvg.vks.accounts.service.vouchers;

import com.techvg.vks.accounts.domain.Vouchers;
import com.techvg.vks.accounts.model.VouchersDto;
import com.techvg.vks.loan.domain.Disbursement;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.model.RepaymentDto;
import com.techvg.vks.share.domain.Shares;
import org.springframework.stereotype.Service;

@Service
public interface LoanVoucherService {
    Vouchers createLoanPaymentVoucher(LoanDetails loanDetails);
    Vouchers createSharePaymentVoucher(LoanDetails loanDetails, Shares shares);

    
    VouchersDto createLoanReceiptVoucher(LoanDetails loan, RepaymentDto repaymentDto);
    VouchersDto updateRepaymentVoucher(VouchersDto vouchersDto);

    Vouchers createDisbursementVoucher(LoanDetails loanDetails, Disbursement disbursement);
}
