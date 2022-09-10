package com.techvg.vks.deposit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techvg.vks.deposit.model.DepositLedgerDto;

@Service
public interface AccountStatementService {

	List<DepositLedgerDto> generateAccountStatement(Long id, String fromDate, String toDate);

}
