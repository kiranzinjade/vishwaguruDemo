package com.techvg.vks.trading.service;

import com.techvg.vks.trading.model.SalesRegisterDto;
import com.techvg.vks.trading.model.SalesRegisterPageList;
import org.springframework.data.domain.Pageable;

public interface SalesRegisterService {

	SalesRegisterDto addSales(SalesRegisterDto salesRegisterDto);

	SalesRegisterPageList listSales(Pageable pageable);

	SalesRegisterDto deleteSalesById(Long id);

	SalesRegisterDto getSalesById(Long id);

}
