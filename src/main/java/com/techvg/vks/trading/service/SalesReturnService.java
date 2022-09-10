package com.techvg.vks.trading.service;

import com.techvg.vks.trading.model.SalesReturnDto;
import com.techvg.vks.trading.model.SalesReturnPageList;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface SalesReturnService {

	SalesReturnDto addSalesReturn(SalesReturnDto salesReturnDto);
	SalesReturnPageList listAllSalesReturn(Pageable pageable);
	SalesReturnPageList listByMember(Pageable pageable, Long memberId);
	List<SalesReturnDto> getSalesReturnByLastRecords();

}
