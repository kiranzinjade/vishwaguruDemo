package com.techvg.vks.trading.repository;

import com.techvg.vks.trading.domain.PurchaseRegister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseRegisterRepository extends JpaRepository<PurchaseRegister, Long> {
	Page<PurchaseRegister> findByIsDeleted(Pageable pageable, boolean b);
	
	@Query(value="select p from PurchaseRegister p where p.product.id=:productId ")
	List<PurchaseRegister> findByProductId(@Param("productId") Long productId);

	@Query(value="select p from PurchaseRegister p where p.purchaseOrder.id=:purchaseOrderId")
	List<PurchaseRegister> findByPurchaseOrderId(@Param("purchaseOrderId") Long purchaseOrderId);
}
