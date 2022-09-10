package com.techvg.vks.loan.reports.defaulterlist;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.repository.LoanDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
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
public class DefaulterListServiceImpl implements DefaulterListService {

	private final DefaulterListMapper defaulterListMapper;
	private final LoanDetailsRepository loanDetailsRepository;
	
	private static final String FILEPATH_DEFAULTER_REPORT = "/DEFAULTER_REPORT.jrxml"; 
	
	@Override
	public byte[] getDefaulterListReport(String loanType) {
		return getdefaulterListReportForAllMember(loanType);
		
	}

	@Override
	public byte[] getdefaulterListReportForAllMember(String loanType) {
		File resource = null;
		byte[] defaulterreportblob = null;
		
		try {
			resource = new ClassPathResource(FILEPATH_DEFAULTER_REPORT).getFile();
			log.info("file got for defaulter list report = "+resource.getName());
			
		}
		catch(IOException e)
		{ 
			throw new NotFoundException("File Not Found...");
		}
		List<LoanDetails> loanDetailslist = loanDetailsRepository.findAllDefaulter(loanType);

		List<DefaulterListWrapper> defaulterListWrapper=this.defaulterListMapper.mapAllDefaulterDataList(loanDetailslist);
		
		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(defaulterListWrapper);

		try {
			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String,Object> params = new HashMap<>();
			params.put("SocietyName","Vividh Karyakari Society");

			JasperPrint print = JasperFillManager.fillReport(report, params,beanDataSource);

			defaulterreportblob = JasperExportManager.exportReportToPdf(print);

		}
		catch(Exception e){
			log.error(e.getLocalizedMessage());
		}
		return defaulterreportblob;
	}

}
