package com.techvg.vks.deposit.service;

import com.techvg.vks.deposit.domain.DepositLedger;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PrintPassbookService {

	List<DepositLedger> listPassbookEntries(Long accountNo);
	List<DepositLedger> listPassbookEntries(Long accountNo, Long transactionId);
}
