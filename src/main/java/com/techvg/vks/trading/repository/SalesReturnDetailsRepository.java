package com.techvg.vks.trading.repository;

import com.techvg.vks.trading.domain.PurchaseRegister;
import com.techvg.vks.trading.domain.SalesReturnDetails;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SalesReturnDetailsRepository extends JpaRepository<SalesReturnDetails, Long> {
	
	SalesReturnDetails findTopByOrderByIdDesc();

	@Query(value="select s from SalesReturnDetails s where s.salesReturn.id=:salesReturnId")
	List<SalesReturnDetails> findBySalesReturnId(@Param("salesReturnId") Long salesReturnId);

}
