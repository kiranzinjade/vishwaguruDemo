package com.techvg.vks.trading.repository;

import com.techvg.vks.trading.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long>{

	@Query(value = "SELECT * FROM stock WHERE product_id =:productId ORDER BY stock.id DESC LIMIT 1",nativeQuery = true)
	Optional<Stock> findByLastRecord(Long productId);
}
