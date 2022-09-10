package com.techvg.vks.trading.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techvg.vks.trading.domain.PurchaseRegister;
import com.techvg.vks.trading.domain.SalesRegister;

@Repository
public interface SalesRegisterRepository extends JpaRepository<SalesRegister, Long>{

	Page<SalesRegister> findByIsDeleted(Pageable pageable,boolean deleted);
	
	@Query(value="select p from SalesRegister p where p.product.id=:productId ")
	List<SalesRegister> findByPoductId(@Param("productId") Long productId);
	
	@Query(value="select s from SalesRegister s where s.salesOrder.id=:salesOrderId")
	List<SalesRegister> findBySalesOrderId(@Param("salesOrderId") Long salesOrderId);
}
