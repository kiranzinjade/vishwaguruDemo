package com.techvg.vks.accounts.service.vouchers;

import com.techvg.vks.accounts.domain.Vouchers;
import com.techvg.vks.trading.domain.SalesOrder;
import org.springframework.stereotype.Service;

@Service
public interface SalesVoucherService {

    Vouchers createSalesVoucher(SalesOrder salesOrder);
}
