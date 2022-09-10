package com.techvg.vks.trading.service;

import com.techvg.vks.trading.model.VendorRegisterDto;
import com.techvg.vks.trading.model.VendorRegisterPageList;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VendorRegisterService {
	
	VendorRegisterDto addVendorDetails(VendorRegisterDto vendorRegisterDto, Authentication authentication);
    VendorRegisterDto updateVendorDetails(Long vendorId, VendorRegisterDto vendorRegisterDto);
    VendorRegisterDto getVendorById(Long vendorId);
    VendorRegisterDto deleteVendorById(Long productId);
    VendorRegisterPageList listVendors(Pageable pageable);
    List<VendorRegisterDto> listAllVendor();
}
