package com.techvg.vks.trading.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techvg.vks.trading.domain.PurchaseOrder;
import com.techvg.vks.trading.domain.PurchaseReturn;

@Repository
public interface PurchaseReturnRepository extends JpaRepository<PurchaseReturn, Long> {

    Page<PurchaseReturn> findAllByIsDeleted(Pageable pageable, boolean b);

    Page<PurchaseReturn> findAllByPurchaseOrder_VendorRegister_Id(Pageable pageable, Long vendorId);
    
    @Query(value ="SELECT * FROM purchase_return ORDER BY purchase_return.id DESC LIMIT 5",nativeQuery = true)
    List<PurchaseReturn>findLastRecords();
}
