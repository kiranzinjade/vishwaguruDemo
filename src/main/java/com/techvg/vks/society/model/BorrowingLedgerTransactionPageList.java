package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class BorrowingLedgerTransactionPageList extends PageImpl<BorrowingLedgerTransactionDto>  {

	public BorrowingLedgerTransactionPageList(List<BorrowingLedgerTransactionDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public BorrowingLedgerTransactionPageList(List<BorrowingLedgerTransactionDto> content) {
        super(content);
    }

}
