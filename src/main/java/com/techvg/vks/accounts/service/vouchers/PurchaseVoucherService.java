package com.techvg.vks.accounts.service.vouchers;

import com.techvg.vks.accounts.domain.Vouchers;
import com.techvg.vks.trading.domain.PurchaseOrder;
import org.springframework.stereotype.Service;

@Service
public interface PurchaseVoucherService {

    Vouchers createPurchaseVoucher(PurchaseOrder purchaseOrder);
}
