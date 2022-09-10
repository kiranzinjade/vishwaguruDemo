package com.techvg.vks.accounts.service;

import com.techvg.vks.accounts.domain.VoucherTrans;
import com.techvg.vks.accounts.domain.Vouchers;
import com.techvg.vks.accounts.model.VoucherTransDto;
import com.techvg.vks.accounts.model.VoucherTransPageList;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VoucherTransService {
    VoucherTransDto addVoucherTrans(VoucherTransDto voucherTransDto);
    VoucherTransDto updateVoucherTrans(Long voucherTransId, VoucherTransDto ledgerAccountsDto, Authentication authentication);
    VoucherTransDto getVoucherTransById(Long voucherTransId);
    VoucherTransPageList listVoucherTrans(Pageable pageable);
    VoucherTransDto deleteVoucherTransById(Long voucherTransId);
    List<VoucherTrans> addVoucherTranEntry(Vouchers vouchers);
}
