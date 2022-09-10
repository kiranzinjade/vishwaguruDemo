package com.techvg.vks.trading.service;

import com.techvg.vks.trading.model.PurchaseOrderDto;
import com.techvg.vks.trading.model.PurchaseOrderPageList;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface PurchaseOrderService {

    PurchaseOrderDto addPurchaseOrder(PurchaseOrderDto purchaseOrderDto);

    PurchaseOrderPageList listPurchaseOrder(Pageable pageable);

    PurchaseOrderDto deletePurchaseOrderById(Long id);

    PurchaseOrderDto getPurchaseOrderById(Long id);

    PurchaseOrderPageList getPurchaseByVendor(Long vendorId, Pageable pageable);

	PurchaseOrderDto listAllPurchaseOrder(Long orderNo);

	List<PurchaseOrderDto> getPurchaseByDesc();
}
