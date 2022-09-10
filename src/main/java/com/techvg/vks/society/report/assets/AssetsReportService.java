package com.techvg.vks.society.report.assets;
import org.springframework.http.ResponseEntity;

public interface AssetsReportService {

	ResponseEntity<?> getAssetsReport(String fromDate,String toDate);

}
