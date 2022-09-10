package com.techvg.vks.trading.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.techvg.vks.trading.model.SundryDebtorDto;
import com.techvg.vks.trading.model.SundryDebtorPageList;

public interface SundryDebtorService {


	SundryDebtorDto addSundryDebitor(SundryDebtorDto sundryDebtorDto, Authentication authentication);

	SundryDebtorDto updateSundry(Long sundryDebtorId, SundryDebtorDto sundryDebtorDto);

	SundryDebtorPageList listAllDebtors(Pageable pageable);

	SundryDebtorDto deleteDebtorsById(long id);

	SundryDebtorDto getDebtorById(long id);
}
