package com.techvg.vks.trading.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techvg.vks.society.domain.Assets;
import com.techvg.vks.trading.domain.SundryCreditor;

public interface SundryCreditorRepository  extends JpaRepository<SundryCreditor, Long>{
	
	 
	@Query(value= "select s from SundryCreditor s  where s.vendor.id =:vendorId  and s.id = (select max(s.id) from SundryCreditor s where s.vendor.id = :vendorId)")
	SundryCreditor findByLastRecord(@Param("vendorId")Long vendorId);
	
	
	  @Query(value="select s from SundryCreditor s where s.vendor.id =:vendorId")
	  List<SundryCreditor> findByVendorId(@Param("vendorId")Long vendorId);
	 
		Page<SundryCreditor> findByisDeleted(Pageable pageable,boolean deleted);

	




}
