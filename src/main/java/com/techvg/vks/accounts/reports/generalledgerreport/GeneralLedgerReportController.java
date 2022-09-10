package com.techvg.vks.accounts.reports.generalledgerreport;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.accounts.domain.GeneralLedger;
import com.techvg.vks.accounts.model.GeneralLedgerDto;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/generalLedger")
public class GeneralLedgerReportController {

	private final GeneralLedgerReportService generalLedgerReportService;
	
	
//	@GetMapping({ "/{ledgerAccountId}" })
//	public ResponseEntity<?> getGeneralLedger(@PathVariable("ledgerAccountId") Long ledgerAccountId) 
//	{ 
//		return generalLedgerReportService.getGeneralLedger(ledgerAccountId);
//	}
	
//	@GetMapping({"/all"})
//	public byte[] getAllGeneralLedger() 	{ 
//		return generalLedgerReportService.getAllGeneralLedger();
//	}
	
	@GetMapping
	public List<ArrayList<GeneralLedgerDto>> getGeneralLedgerList() 
	{ 
		return generalLedgerReportService.getGeneralLedgerList();
	}

}
