package com.techvg.vks.trading.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.techvg.vks.trading.domain.PurchaseOrder;
import org.springframework.transaction.annotation.Transactional;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    Page<PurchaseOrder> findByIsDeleted(Pageable pageable, boolean b);
    
    @Query(value ="SELECT * FROM purchase_order where purchase_order.vendorregister_id=:vendorId ORDER BY purchase_order.id DESC ",nativeQuery = true)
    List<PurchaseOrder>findLastRecordsByVendorId(Long vendorId);
    
    @Query(value="SELECT * FROM purchase_order  ORDER BY purchase_order.id DESC",nativeQuery = true)
    List<PurchaseOrder>findLastRecords();
    
    PurchaseOrder findByOrderNo(Long orderNo);

    @Transactional
    @Modifying
    @Query(value="update PurchaseOrder po set po.voucherNo=:voucherNo where po.id=:purchaseOrderId ")
    void updateVoucherNo(Long voucherNo, Long purchaseOrderId);

}
