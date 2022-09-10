package com.techvg.vks.trading.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.techvg.vks.trading.domain.SundryCreditorTransaction;

public interface SundryCreditorTransactionRepository extends JpaRepository<SundryCreditorTransaction, Long>{

	@Query(value="select s from SundryCreditorTransaction s where s.sundryCreditor.id =:sundryCreditor")
	  List<SundryCreditorTransaction> findByCreditorId(@Param("sundryCreditor")Long sundryCreditor);
}
