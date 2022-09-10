package com.techvg.vks.accounts.reports.generalledgerreport;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.techvg.vks.accounts.domain.GeneralLedger;
import com.techvg.vks.accounts.model.GeneralLedgerDto;
import com.techvg.vks.loan.reports.loandisbursement.LoanDisbursementReportWrapper;

import net.sf.jasperreports.engine.JRException;

public interface GeneralLedgerReportService {

//	public ResponseEntity<?> getGeneralLedger(Long ledgerAccountId);

//	public byte[] getAllGeneralLedger();
	
//	public byte[] getGeneralledgerReport(List<GeneralLedgerReportWrapper> generalLedgerReportWrapperlist);

	public List<ArrayList<GeneralLedgerDto>> getGeneralLedgerList();

}
