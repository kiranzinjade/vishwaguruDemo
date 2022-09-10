package com.techvg.vks.accounts.service.vouchers;

import com.techvg.vks.accounts.domain.Vouchers;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.society.domain.LoanProduct;
import org.springframework.stereotype.Service;

@Service
public interface BorrowingVoucherService {

    Vouchers createBorrowingReceiptVoucher(LoanProduct loanProduct);
    Vouchers createBorrowingReceiptVoucherForMLLoan(LoanDetails loanDetails);

}
