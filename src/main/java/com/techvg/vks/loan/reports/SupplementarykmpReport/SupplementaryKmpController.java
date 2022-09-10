package com.techvg.vks.loan.reports.SupplementarykmpReport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/api/supplementarykmp")
public class SupplementaryKmpController {
	
	private final SupplementaryKmpService supplementaryKmpService;

	@GetMapping({ "/{loanDemandId}" })
	public ResponseEntity<?> geSupplementaryKmpReport(@PathVariable("loanDemandId") Long loanDemandId)
	{ 
		return supplementaryKmpService.getSupplementaryKmpReport(loanDemandId);
		
	}
	
	
}
