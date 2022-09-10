package com.techvg.vks.loan.reports.loandisbursement;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.repository.LoanDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
@RequiredArgsConstructor
@Slf4j

public class LoanDisbursementReportServiceImpl implements LoanDisbursementReportService{

	private final LoanDisbursementReportMapper loanDisbursementMapper;
	private final LoanDetailsRepository loanDetailsRepository;
	private static final String FILEPATH_REPORT = "/MidLongTermLoan.jrxml"; 

	
	@Override
	public ResponseEntity<?> getLoanDisbursement(String loanType) {
		List<LoanDetails> loanDetails = loanDetailsRepository.findByLoanType(loanType);
		List<LoanDisbursementReportWrapper> loanDisbursementWrapperlist=this.loanDisbursementMapper.mapAllDataList(loanDetails);
	
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_PDF).body(getAllLoanDisbursementList(loanDisbursementWrapperlist));
	}

	@Override
	public ResponseEntity<?> getLoanDisbursement(Long loanId) {
		LoanDetails loanDetails = loanDetailsRepository.findByLoanId(loanId);
		ArrayList<LoanDetails> loanDetailslist=new ArrayList<>();
		loanDetailslist.add(loanDetails);
		List<LoanDisbursementReportWrapper>loanDisbursementWrapperlist=this.loanDisbursementMapper.mapAllDataList(loanDetailslist);
	
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_PDF).body(getAllLoanDisbursementList(loanDisbursementWrapperlist));
	}

	@Override
	public byte[] getAllLoanDisbursementList(List<LoanDisbursementReportWrapper> loanDisbursementWrapperlist) {
		File resource = null;
		byte[] reportblob = null;
		
		try {
			resource = new ClassPathResource(FILEPATH_REPORT).getFile();			
		}
		catch(IOException e)
		{ 
			throw new NotFoundException("File Not Found...");
		}
		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(loanDisbursementWrapperlist);
		try {
			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("SocietyName","MEDIUM TERM/LONG TERM DISBURSEMENT CUM LOAN LEDGER");

			JasperPrint print = JasperFillManager.fillReport(report, params,beanDataSource);

			reportblob = JasperExportManager.exportReportToPdf(print);

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return reportblob;
	}



}

