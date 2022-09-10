package com.techvg.vks.trading.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.techvg.vks.trading.model.PurchaseRegisterDto;
import com.techvg.vks.trading.model.PurchaseRegisterPageList;

@Service
public interface PurchaseRegisterService {

	PurchaseRegisterDto addPurchase(PurchaseRegisterDto purchaseRegisterDto, Authentication authentication);

	PurchaseRegisterPageList listPurchase(Pageable pageable);

	PurchaseRegisterDto deletePurchaseById(Long id);

	PurchaseRegisterDto getPurchaseById(Long id);

}
