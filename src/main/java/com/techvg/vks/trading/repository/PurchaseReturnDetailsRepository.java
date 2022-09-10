package com.techvg.vks.trading.repository;

import com.techvg.vks.trading.domain.PurchaseReturnDetails;
import com.techvg.vks.trading.domain.SalesReturnDetails;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PurchaseReturnDetailsRepository extends JpaRepository<PurchaseReturnDetails, Long> {
	
	PurchaseReturnDetails findTopByOrderByIdDesc();

	@Query(value="select p from PurchaseReturnDetails p where p.purchaseReturn.id=:PurchaseReturnId")
	List<PurchaseReturnDetails> findByPurchaseReturnId(@Param("PurchaseReturnId") Long PurchaseReturnId);
}
