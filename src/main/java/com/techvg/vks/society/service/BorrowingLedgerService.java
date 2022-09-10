package com.techvg.vks.society.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.techvg.vks.society.model.BorrowingLedgerDto;
import com.techvg.vks.society.model.BorrowingLedgerPageList;

public interface BorrowingLedgerService {

	BorrowingLedgerDto addborrowingDetails(BorrowingLedgerDto borrowingLedgerDto);

	BorrowingLedgerPageList listborrow(Pageable pageable);

	BorrowingLedgerDto deleteBorrowById(Long id);

	BorrowingLedgerDto updateBorrowList(Long id, BorrowingLedgerDto borrowingLedgerDto);

	BorrowingLedgerDto getBorrowListById(Long id);

	List<BorrowingLedgerDto> listLedger();

}
