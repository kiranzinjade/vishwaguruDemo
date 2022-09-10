package com.techvg.vks.loan.reports.AcknowledgmentOfDebt;

import com.techvg.vks.config.LoanConstants;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.Amortization;
import com.techvg.vks.loan.repository.AmortizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AcknowledgmentOfDebtServiceImpl implements AcknowledgmentOfDebtService {
	private final AcknowledgmentOfDebtMapper acknowledgmentOfDebtMapper; 
	private final AmortizationRepository amortizationRepository;
	private static final String FILEPATH_AcknowledgmentOfDebt_REPORT = "/Acknowledgement_of_Debit.jrxml"; 

	
	@Override
	public ResponseEntity<?> getAcknowledgmentOfDebtReport(String endYearDate) {
		log.info(" End Year received (service) successfully");
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_PDF).body(creatingAcknowledgmentOfDebtReport(endYearDate));

	}


	public byte[] creatingAcknowledgmentOfDebtReport(String endYearDate) {
		File resource = null;
		byte[] acknowledgmentOfDebtblob = null;
		
		try {
			resource = new ClassPathResource(FILEPATH_AcknowledgmentOfDebt_REPORT).getFile();
			log.info("file got for AcknowledgmentOfDebt Report = "+resource.getName());
			
		}
		catch(IOException e)
		{ 
			throw new NotFoundException("File Not Found...");
		}

		String loanStatus=LoanConstants.LOAN_Active;// Disscuss to Issuse Only paricularYear Repot not Previous f Loan
		List<Amortization> amortizationlist=amortizationRepository.findByOutstanding(loanStatus);
		List<AcknowledgmentOfDebtWrapper> acknowledgmentOfDebtWrapper=null;
		acknowledgmentOfDebtWrapper=this.acknowledgmentOfDebtMapper.mapAllAcknowledgmentOfDebtDataList(amortizationlist);
			
			JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(acknowledgmentOfDebtWrapper);

		try {
			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String,Object> params = new HashMap<>();
			params.put("SocietyName","Vividh Karyakari Society");
			JasperPrint print = JasperFillManager.fillReport(report,params,beanDataSource);
			acknowledgmentOfDebtblob = JasperExportManager.exportReportToPdf(print);
		
				}
		catch(Exception e){
			log.error(e.getLocalizedMessage());
		}
		
		return acknowledgmentOfDebtblob;
	}

}
