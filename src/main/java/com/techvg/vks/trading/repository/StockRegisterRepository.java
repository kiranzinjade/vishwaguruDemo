package com.techvg.vks.trading.repository;

import com.techvg.vks.trading.domain.StockRegister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface StockRegisterRepository extends JpaRepository<StockRegister, Long>{


	@Query(value = "SELECT * FROM stock_register WHERE product_id =:productId ORDER BY stock_register.id DESC LIMIT 1",nativeQuery = true)
	Optional<StockRegister> findByProductId(@Param("productId") Long productId);

	@Query(value = "SELECT * FROM stock_register WHERE product_id =:productId AND transaction_date<:date ORDER BY stock_register.id DESC LIMIT 1",nativeQuery = true)
	StockRegister findLastRecordByDate(@Param("productId") Long productId,@Param("date") Date date);
	
	@Query(value="select s from StockRegister s where s.product.id=:productId AND s.transactionDate>:date AND s.transactionDate<:nextDate")
	List<StockRegister> findByDate(@Param("productId") Long productId,@Param("date") Timestamp date,@Param("nextDate") Timestamp nextDate);

	
	@Query(value="select d from StockRegister d where d.transactionDate BETWEEN :fromdate4 and :todate5")
	List<StockRegister> findByDateFromtoTo(@Param("fromdate4")Date fromdate4,@Param("todate5")Date todate5);
	

	@Query(value="select s from StockRegister  s where s.product.id=:productId AND s.transactionDate>=:fromdate4 order by s.transactionDate asc")
	List<StockRegister> findByDateFromto(@Param("fromdate4")Date fromdate4,@Param("productId") Long productId);

	Page<StockRegister> findByIsDeleted(Pageable pageable, boolean deleted);
	
	List<StockRegister> findByProductIdAndTransType(Long productId, String PURCHASE_TRANS);
	
	@Query(value="select s from StockRegister s where s.product.id=:productId AND s.id=(select max(s.id) from StockRegister s where s.product.id=:productId)")
	StockRegister findByIdCurrentStock(@Param("productId")Long productId);
	

	@Query(value="select s from StockRegister s  where  s.product.id=:productId AND  s.transactionDate  BETWEEN :fromdate4 and :todate5")
	List<StockRegister> findByProductIdDateformatto(@Param("productId")Long productId,@Param("fromdate4")Date fromdate4,@Param("todate5")Date todate5);
}
