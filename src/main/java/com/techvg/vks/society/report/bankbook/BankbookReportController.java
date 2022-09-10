package com.techvg.vks.society.report.bankbook;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.society.model.BankBookPrintDto;
import com.techvg.vks.society.model.SocietyBankTransactionDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/bankbookreport")
public class BankbookReportController {
	
	private final BankbookReportService bankbookReportService;

//	@GetMapping({ "/{accountNo}" })
//	public ResponseEntity<?> getBankListReport(@PathVariable("accountNo") Long accountNo)
//	{ 
//		return bankbookReportService.getBankbookListReport(accountNo);
//	}

	
	@GetMapping({"/{accountNo}/{transDate}"})
	public ResponseEntity<BankBookPrintDto>getBankList(@PathVariable("accountNo") Long accountNo,@PathVariable("transDate") String  transDate)
	{
		return new ResponseEntity<>(bankbookReportService.getBankbookListReport(accountNo,transDate), HttpStatus.OK);
	}
}
