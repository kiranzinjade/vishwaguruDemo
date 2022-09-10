package com.techvg.vks.accounts.service;

import com.techvg.vks.accounts.domain.VoucherType;
import com.techvg.vks.accounts.model.VoucherTypeDto;
import com.techvg.vks.accounts.model.VoucherTypePageList;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface VoucherTypeService {
    VoucherTypeDto addVoucherType(VoucherTypeDto voucherTypeDto);
    VoucherTypeDto updateVoucherType(Long voucherTypeId, VoucherTypeDto voucherTypeDto, Authentication authentication);
    VoucherTypeDto getVoucherTypeById(Long voucherTypeId);
    VoucherTypeDto deleteVoucherTypeById(Long voucherTypeId);
    VoucherTypePageList listVoucherType(Pageable pageable);
	List<VoucherTypeDto> listVouchers();
    List<VoucherTypeDto> listGeneralVouchers();
    VoucherType getVoucherTypeByName(String name);
}
