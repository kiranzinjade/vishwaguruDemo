package com.techvg.vks.deposit.reports.SavingAccountList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.deposit.reports.fdreport.FdReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor

@CrossOrigin(origins = "*")
@RequestMapping("/api/savingAccountList")
public class SavingAccountListController {
	
	@Autowired
	SavingAccountListService savingAccountListService;
	
	@GetMapping
	public byte[] getMemberwiseShareReport()
	{ 
		return savingAccountListService.getsavingAccountListReport();
	}
}
