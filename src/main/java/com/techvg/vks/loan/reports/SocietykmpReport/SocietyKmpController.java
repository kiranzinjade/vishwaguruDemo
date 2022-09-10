package com.techvg.vks.loan.reports.SocietykmpReport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/api/societykmp")
public class SocietyKmpController {
	
	private final SocietyKmpService service;
	
	@PostMapping({"/generate/{cropLoanDemandId}" })
	public ResponseEntity<?> geSocietyKmpReportForCropDemand(@PathVariable("cropLoanDemandId") Long cropLoanDemandId)
	{
		return service.generateSocietyKmpReport(cropLoanDemandId);
	}

	@PostMapping({"/generateall" })
	public ResponseEntity<?> geKmpReport(List<Long> cropLoanDemandIdList)
	{
		return service.generateAllSocietyKmpReports(cropLoanDemandIdList);
	}
}
