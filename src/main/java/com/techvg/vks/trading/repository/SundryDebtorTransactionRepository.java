package com.techvg.vks.trading.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.techvg.vks.trading.domain.SundryDebtorTransaction;
@Repository
public interface SundryDebtorTransactionRepository extends JpaRepository<SundryDebtorTransaction, Long>{
	
	@Query(value="select s from SundryDebtorTransaction s where s.sundryDebtor.id=:sundryDebtorId ")
	List<SundryDebtorTransaction> findBysundryDebtorId(@Param("sundryDebtorId") Long sundryDebtorId);

}
