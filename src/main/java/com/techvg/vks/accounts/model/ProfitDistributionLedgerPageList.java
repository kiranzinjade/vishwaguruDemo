package com.techvg.vks.accounts.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class ProfitDistributionLedgerPageList  extends PageImpl<ProfitDistributionLedgerDto>{
	
	public ProfitDistributionLedgerPageList(List<ProfitDistributionLedgerDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public ProfitDistributionLedgerPageList(List<ProfitDistributionLedgerDto> content) {
        super(content);
    }

}
