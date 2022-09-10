package com.techvg.vks.share.reports.ShareCapitalLedger;

import org.springframework.stereotype.Service;

@Service
public interface ShareCapitalLedgerService {

	byte[] generateShareCapitalLedgerReport(Long memberId);

}
