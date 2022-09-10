package com.techvg.vks.trading.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techvg.vks.trading.model.SalesOrderDto;
import com.techvg.vks.trading.model.SalesOrderPageList;

@Service
public interface SalesOrderService {

    SalesOrderDto addSalesOrder(SalesOrderDto salesOrderDto);

    SalesOrderPageList listSalesOrder(Pageable pageable);

    SalesOrderDto deleteSalesOrderById(Long id);

    SalesOrderDto getSalesOrderById(Long id);

    SalesOrderPageList getSalesByMember(Long memberId, Pageable pageable);

    SalesOrderDto listAllSalesOrder(Long orderNo);

	List listAllSales();

	List<SalesOrderDto> getSalesByDesc();

}
