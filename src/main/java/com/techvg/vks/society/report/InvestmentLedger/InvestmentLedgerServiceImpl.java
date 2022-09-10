package com.techvg.vks.society.report.InvestmentLedger;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.SocietyInvestment;
import com.techvg.vks.society.domain.SocietyInvestmentDetails;
import com.techvg.vks.society.repository.SocietyInvestmentDetailsRepository;
import com.techvg.vks.society.repository.SocietyInvestmentRepository;
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
public class InvestmentLedgerServiceImpl implements InvestmentLedgerService{
	
	private final InvestmentLedgerMapper  investmentLedgerMapper;
	private final SocietyInvestmentRepository societyInvestmentRepository;
	private final SocietyInvestmentDetailsRepository  societyInvestmentDetailsRepository;
	
	private static final String FILEPATH_INVESTMENT_REPORT = "/Investment_Ledger.jrxml";
	
	

	@Override
	public byte[] getInvestmentDetailsReport(Long societyInvestmentId) {
		return getInvestmentDetails(societyInvestmentId);
	}
	
	@Override
	public byte[] getInvestmentDetails(Long societyInvestmentId) {
		File resource = null;
		byte[] investmentreportblob = null;
		
		try {
			resource = new ClassPathResource(FILEPATH_INVESTMENT_REPORT).getFile();
			log.info("file got for investment list report = "+resource.getName());
			
		}
		catch(IOException e)
		{ 
			throw new NotFoundException("File Not Found...");
		}
			
		//List<SocietyInvestment> investmentDetailslist = societyInvestmentRepository.findBySocietyInvestmentId(societyInvestmentId);

		List<SocietyInvestmentDetails> investmentDetailslist = societyInvestmentDetailsRepository.findBySocietyInvestmentId(societyInvestmentId);
	
		List<InvestmentLedgerWrapper> investmentListWrapper=this.investmentLedgerMapper.mapAllRegisterDataList(investmentDetailslist);
		
		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(investmentListWrapper);

		try {
			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String,Object> params = new HashMap<>();
			params.put("SocietyName","Vividh Karyakari Society");

			JasperPrint print = JasperFillManager.fillReport(report, params,beanDataSource);

			investmentreportblob = JasperExportManager.exportReportToPdf(print);

		}
		catch(Exception e){
			log.error(e.getLocalizedMessage(), e.fillInStackTrace());
			e.printStackTrace();
		}
		return investmentreportblob;
	}

	}


