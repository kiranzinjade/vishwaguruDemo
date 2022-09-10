package com.techvg.vks.trading.reports.SundryCreditorReport;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.trading.domain.SundryCreditor;
import com.techvg.vks.trading.domain.SundryCreditorTransaction;
import com.techvg.vks.trading.repository.SundryCreditorRepository;
import com.techvg.vks.trading.repository.SundryCreditorTransactionRepository;

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

public class SundryCreditorReportServiceImpl implements SundryCreditorReportService {
	
	private final SundryCreditorReportMapper sundryCreditorReportMapper;
	private final SundryCreditorTransactionRepository sundryCreditorTransactionRepository;
	
	private static final String FILEPATH_SUNDRY_CREDITOR_REPORT = "/Sundry_Creditor.jrxml";

	@Override
	public byte[] generateSundryCreditorReport(Long CreditorId) {
		File resource = null;
		byte[] sundryCreditorreportblob = null;
		
		try {
			resource = new ClassPathResource(FILEPATH_SUNDRY_CREDITOR_REPORT).getFile();
			log.info("file got for defaulter list report = "+resource.getName());
			
		}
		catch(IOException e)
		{ 
			throw new NotFoundException("File Not Found...");
		}

		List<SundryCreditorTransaction> sundryCreditorlist = sundryCreditorTransactionRepository.findByCreditorId(CreditorId);

		System.out.println("SundryCreditorlist"+sundryCreditorlist);
		List<SundryCreditorReportWrapper> sundryCreditorReportWrapper=this.sundryCreditorReportMapper.mapAllSundryCreditorDataList(sundryCreditorlist);
	
		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(sundryCreditorReportWrapper);


		

		try {
			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("SocietyName","Vividh Karyakari Society");

			JasperPrint print = JasperFillManager.fillReport(report, params,beanDataSource);

			sundryCreditorreportblob = JasperExportManager.exportReportToPdf(print);

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return sundryCreditorreportblob;
	}


}
