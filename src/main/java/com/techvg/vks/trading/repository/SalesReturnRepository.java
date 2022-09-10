package com.techvg.vks.trading.repository;

import com.techvg.vks.trading.domain.PurchaseReturn;
import com.techvg.vks.trading.domain.SalesReturn;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesReturnRepository extends JpaRepository<SalesReturn, Long>{

    Page<SalesReturn> findAllByIsDeleted(Pageable pageable, boolean b);

    Page<SalesReturn> findAllBySalesOrder_Member_Id(Pageable pageable, Long memberId);
     
    @Query(value ="SELECT * FROM sales_return ORDER BY sales_return.id DESC LIMIT 5",nativeQuery = true)
    List<SalesReturn>findLastRecords();
}
