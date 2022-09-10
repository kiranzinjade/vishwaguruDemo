package com.techvg.vks.trading.service;

import com.techvg.vks.trading.model.PurchaseReturnPageList;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techvg.vks.trading.model.PurchaseOrderDto;
import com.techvg.vks.trading.model.PurchaseReturnDto;

@Service
public interface PurchaseReturnService {

	PurchaseReturnDto addPurchaseReturn( PurchaseReturnDto purchaseReturnDto);
	PurchaseReturnPageList listAllPurchaseReturn(Pageable pageable);
	PurchaseReturnPageList listByVendor(Pageable pageable, Long vendorId);
	List<PurchaseReturnDto> getPurchaseReturnByLastRecords();

}
