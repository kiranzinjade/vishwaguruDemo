package com.techvg.vks.accounts.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.techvg.vks.accounts.model.ProfitDistributionLedgerDto;
import com.techvg.vks.accounts.model.ProfitDistributionLedgerPageList;

public interface ProfitDistributionLedgerService {

	ProfitDistributionLedgerDto addLedgerDetails(ProfitDistributionLedgerDto profitDistributionLedgerDto, Authentication authentication);

	ProfitDistributionLedgerPageList listLedger(Pageable pageable);

	ProfitDistributionLedgerDto deleteLedgerById(Long id);

	ProfitDistributionLedgerDto updateLedgerList(Long id, ProfitDistributionLedgerDto profitDistributionLedgerDto);

	ProfitDistributionLedgerDto getLedgerListById(Long id);

}
