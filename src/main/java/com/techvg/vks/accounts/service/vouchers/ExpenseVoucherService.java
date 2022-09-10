package com.techvg.vks.accounts.service.vouchers;

import com.techvg.vks.accounts.model.VouchersDto;
import com.techvg.vks.society.model.ExpenditureRegisterDto;
import org.springframework.stereotype.Service;

@Service
public interface ExpenseVoucherService {

    VouchersDto createExpenseVoucher(ExpenditureRegisterDto expenditureRegisterDto);
    VouchersDto updateExpenseVoucher(VouchersDto vouchersDto);
}
