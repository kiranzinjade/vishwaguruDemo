package com.techvg.vks.trading.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.techvg.vks.trading.domain.SalesOrder;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {

    Page<SalesOrder> findByIsDeleted(Pageable pageable, boolean b);

    @Query(value ="SELECT * FROM sales_order where sales_order.member_id=:memberId ORDER BY sales_order.id DESC",nativeQuery = true)
    List<SalesOrder>findLastRecordsByMemberId(Long memberId);

    @Query(value="SELECT * FROM sales_order  ORDER BY sales_order.id DESC",nativeQuery = true)
    List<SalesOrder>findLastRecords();
    
    @Transactional
    @Modifying
    @Query(value="update SalesOrder so set so.voucherNo=:voucherNo where so.id=:salesOrderId ")
    void updateVoucherNo(Long voucherNo, Long salesOrderId);

    SalesOrder findByOrderNo(Long orderNo);
    
    @Query(value= "select count(*) from sales_order where month(sales_order.sales_date)=:month and year(sales_order.sales_date)=:year",nativeQuery = true)
    Integer getSalesCount(@Param("month")Integer month,@Param("year")Integer year);
    
    



}
