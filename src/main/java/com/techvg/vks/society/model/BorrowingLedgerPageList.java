package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.techvg.vks.trading.model.PurchaseRegisterDto;

public class BorrowingLedgerPageList extends PageImpl<BorrowingLedgerDto> {
	
	public BorrowingLedgerPageList(List<BorrowingLedgerDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public BorrowingLedgerPageList(List<BorrowingLedgerDto> content) {
        super(content);
    }

}
