package com.techvg.vks.membership.reports.MemberRegReport;

import org.springframework.http.ResponseEntity;

public interface MemberRegReportService {

	ResponseEntity<?> getMemberRegList(Long memberId);

}
