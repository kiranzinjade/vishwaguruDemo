package com.techvg.vks.accounts.service;

import com.techvg.vks.accounts.model.VouchersDto;
import com.techvg.vks.accounts.model.VouchersPageList;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VouchersService {
    VouchersDto addVouchers(VouchersDto vouchersDto, Authentication authentication);
    VouchersDto updateVouchers(Long vouchersId, VouchersDto vouchersDto, Authentication authentication);
    VouchersDto getVouchersById(Long vouchersId);
    VouchersDto deleteVouchersById(Long vouchersId);
    VouchersPageList listVouchers(Pageable pageable);
    VouchersDto authoriseVoucher(Long vouchersId);
    boolean authoriseVoucher(List<Long> vouchersIdList);
}
