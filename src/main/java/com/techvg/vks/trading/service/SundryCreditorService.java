package com.techvg.vks.trading.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techvg.vks.trading.model.SundryCreditorDto;
import com.techvg.vks.trading.model.SundryCreditorPageList;
@Service
public interface SundryCreditorService {

	SundryCreditorDto addTransactions(SundryCreditorDto sundryCreditorDto);

	SundryCreditorDto updateSundry(Long sundryCreditorId, SundryCreditorDto sundryCreditorDto);


	SundryCreditorPageList listAllCreditors(Pageable pageable);

	SundryCreditorDto deleteCreditorById(long id);

	SundryCreditorDto getCreditorById(long id);

}
