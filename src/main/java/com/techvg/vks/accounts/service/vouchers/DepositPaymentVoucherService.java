package com.techvg.vks.accounts.service.vouchers;

import com.techvg.vks.accounts.domain.Vouchers;
import com.techvg.vks.deposit.domain.Deposit;
import org.springframework.stereotype.Service;

@Service
public interface DepositPaymentVoucherService {

    Vouchers depositPaymentVoucher(Deposit deposit);
}
