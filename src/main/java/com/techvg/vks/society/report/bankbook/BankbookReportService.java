package com.techvg.vks.society.report.bankbook;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.techvg.vks.society.model.BankBookPrintDto;
import com.techvg.vks.society.model.SocietyBankTransactionDto;

public interface BankbookReportService {

//	public byte[] getBankListReport(Long accountNo);
//	ResponseEntity<?> getBankbookListReport(Long accountNo);
	
	BankBookPrintDto getBankbookListReport(Long accountNo,String transDate);

}
