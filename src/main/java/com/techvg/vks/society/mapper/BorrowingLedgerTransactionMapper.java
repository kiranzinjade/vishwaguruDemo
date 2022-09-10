package com.techvg.vks.society.mapper;

import org.mapstruct.Mapper;

import com.techvg.vks.society.domain.BorrowingLedgerTransaction;
import com.techvg.vks.society.model.BorrowingLedgerTransactionDto;

@Mapper(componentModel = "spring")
public interface BorrowingLedgerTransactionMapper {

	BorrowingLedgerTransactionDto borrowingLedgerTransactionToBorrowingLedgerTransactionDto(BorrowingLedgerTransaction borrowingLedgerTransaction);
	BorrowingLedgerTransaction borrowingLedgerTransactionDtoToBorrowingLedgerTransaction(BorrowingLedgerTransactionDto orrowingLedgerTransactionDto);

	
}
