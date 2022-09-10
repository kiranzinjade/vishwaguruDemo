package com.techvg.vks.accounts.repository;

import com.techvg.vks.accounts.domain.CashBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CashBookRepository extends JpaRepository<CashBook, Long> {
	
	
	@Query(value=" select c from CashBook c where date(c.transDate) = :transDate ")
	List<CashBook> findByCashBookRecord(@Param("transDate") Date transDate);
	
	@Query(value="select c from CashBook c where date(c.transDate)= subdate(:transDate, 1) and c.id = (select max(c.id) from  CashBook c  where date(c.transDate)= subdate(:transDate, 1))")
	CashBook findByLastRecord(@Param("transDate")Date transDate);

	@Query(value="select c from CashBook c where date(c.transDate)=:transDate and c.id = (select max(c.id) from  CashBook c  where date(c.transDate)=:transDate)")
	CashBook findByLastRecordOfTheDay(@Param("transDate")Date transDate);

	CashBook findTopByOrderByIdDesc();
}
