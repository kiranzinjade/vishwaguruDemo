package com.techvg.vks.society.repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techvg.vks.society.domain.AssetsRegistration;
import com.techvg.vks.trading.domain.StockRegister;

public interface AssetsRegistrationRepository extends JpaRepository<AssetsRegistration, Long>{
	
	Page<AssetsRegistration> findByisDeleted(Pageable pageable,boolean deleted);

	@Query(value = "SELECT * FROM assets_registration WHERE assets_id =:assetsId ORDER BY assets_registration.id DESC LIMIT 1",nativeQuery = true)
	Optional<AssetsRegistration> findByLastRecord(Long assetsId);
	
	@Query(value="select s from AssetsRegistration s where s.date BETWEEN :fromdate and :todate order by s.date asc")
	List<AssetsRegistration> findByDateFromtoTo(@Param("fromdate")Date fromdate1,@Param("todate")Date todate1);
	
	@Query(value = "SELECT * FROM assets_registration WHERE assets_id =:assetId ORDER BY assets_registration.id",nativeQuery = true)
	List<AssetsRegistration> findByAssetId(Long assetId);

	
}
