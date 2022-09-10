package com.techvg.vks.accounts.service;

import com.techvg.vks.accounts.model.VoucherDetailsDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface VoucherDetailsService {

    VoucherDetailsDto addVoucherDetails(VoucherDetailsDto voucherDetailsDto);
    VoucherDetailsDto updateVoucherDetails(Long transId, VoucherDetailsDto voucherDetailsDto, Authentication authentication);
    VoucherDetailsDto getVoucherDetailsById(Long transId);
    VoucherDetailsDto deleteVoucherDetailsById(Long transId);
}
