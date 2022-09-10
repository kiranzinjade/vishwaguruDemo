package com.techvg.vks.society.service;

import org.springframework.stereotype.Service;

import com.techvg.vks.society.model.BorrowingLedgerTransactionDto;

@Service
public interface BorrowingLedgerTransactionService {

	BorrowingLedgerTransactionDto addTransactionDetails(BorrowingLedgerTransactionDto borrowingLedgerTransactionDto,
			String transactionCriteria);

	double getTotalLoanAmount();

	double getBalanceLoanAmount();

	
}
