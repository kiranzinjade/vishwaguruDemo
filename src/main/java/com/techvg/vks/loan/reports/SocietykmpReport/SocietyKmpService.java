package com.techvg.vks.loan.reports.SocietykmpReport;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SocietyKmpService {


	byte[] creatingSocietyKmpReport(List<SocietyKmpReportWrapper> wrapperList);

	ResponseEntity<?> generateSocietyKmpReport(Long cropLoanDemandId);
	ResponseEntity<?> generateAllSocietyKmpReports(List<Long> cropLoanDemandIdList);
}
