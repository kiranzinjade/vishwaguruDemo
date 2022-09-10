package com.techvg.vks.society.report.assets;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/assetsReport")
public class AssetsReportController {
	
	private final AssetsReportService assetsReportService;
	
	@GetMapping({ "/{fromDate}/{toDate}" })
	public ResponseEntity<?>getAssetsReport(@PathVariable("fromDate") String fromDate,@PathVariable("toDate") String toDate)
	{ 
			return assetsReportService.getAssetsReport(fromDate,toDate);	
	}

}
