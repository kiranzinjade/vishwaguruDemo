package com.techvg.vks.deposit.reports.MaturityRegisterDeposit;

import com.techvg.vks.deposit.domain.Deposit;
import com.techvg.vks.deposit.repository.DepositRepository;
import com.techvg.vks.exceptions.NotFoundException;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MaturityRegisterDepositServiceImpl implements MaturityRegisterDepositService {
	private final MaturityRegisterDepositMapper maturityRegisterDepositMapper; 
	private final DepositRepository depositRepository;
	private static final String FILEPATH_Maturity_REPORT = "/Maturity_register.jrxml"; 
	
	@Override
	public ResponseEntity<?> getMaturityReport(String fromDate, String toDate) {
		log.info("Deposit Id received (service) successfully");
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_PDF).body(CreatingMaturityReport(fromDate,toDate));

	}
	


	@Override
	public byte[] CreatingMaturityReport(String fromDate, String toDate) {
		File resource = null;
		byte[] maturityblob = null;
		
		try {
			resource = new ClassPathResource(FILEPATH_Maturity_REPORT).getFile();
			log.info("file got for Kmp Report = "+resource.getName());
			
		}
		catch(IOException e)
		{ 
			throw new NotFoundException("File Not Found...");
		}
		
		
		         //convert string into Local date format 
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
				LocalDate fromdate1 = LocalDate.parse(fromDate, formatter);

				LocalDate todate2 = LocalDate.parse(toDate, formatter);

				// convert Local date into date format with hh:mm:ss
				Instant instant = fromdate1.atStartOfDay(ZoneId.systemDefault()).toInstant();
				Date fromdate4 = Date.from(instant);
				
				Instant instant1 = todate2.atStartOfDay(ZoneId.systemDefault()).toInstant();
				Date todate5 = Date.from(instant1);
			
		List<Deposit> depositlist=depositRepository.findByDeposit(fromdate4, todate5);
		List<MaturityRegisterDepositWrapper> maturityRegisterDepositWrapper=null; 
		maturityRegisterDepositWrapper=this.maturityRegisterDepositMapper.mapAllMaturityDataList(depositlist);
			
			JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(maturityRegisterDepositWrapper);

		try {
			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String,Object> params = new HashMap<>();
			params.put("SocietyName","Vividh Karyakari Society");

			JasperPrint print = JasperFillManager.fillReport(report,params,beanDataSource);
			maturityblob = JasperExportManager.exportReportToPdf(print);
		
				}
		catch(Exception e){
			log.error(e.getLocalizedMessage());
		}
		
		return maturityblob;
	
	}




}
