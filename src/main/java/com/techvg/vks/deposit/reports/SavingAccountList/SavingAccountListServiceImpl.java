package com.techvg.vks.deposit.reports.SavingAccountList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.techvg.vks.deposit.domain.SavingAccount;
import com.techvg.vks.deposit.repository.SavingAccountRepository;
import com.techvg.vks.exceptions.NotFoundException;

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
public class SavingAccountListServiceImpl implements SavingAccountListService{
	
	private final SavingAccountListMapper savingAccountListMapper;
	private final SavingAccountRepository savingAccountRepository;
	
	private static final String FILEPATH_SAVINGACCOUNTLIST_REPORT = "/SavingAccountList.jrxml";

	@Override
	public byte[] getsavingAccountListReport() {
		
		return (getsavingAccountListReportforAll());
	}

	@Override
	public byte[] getsavingAccountListReportforAll() {
		File resource = null;
		byte[] savingAccountreportblob = null;
		
		try {
			resource = new ClassPathResource(FILEPATH_SAVINGACCOUNTLIST_REPORT).getFile();
			log.info("file got for defaulter list report = "+resource.getName());
			
		}
		catch(IOException e)
		{ 
			throw new NotFoundException("File Not Found...");
		}

		List<SavingAccount> savingAccountlist = savingAccountRepository.findAll();

		List<SavingAccountListWrapper> savingAccountListWrapper=this.savingAccountListMapper.mapAllSavingAccountDataList(savingAccountlist);

		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(savingAccountListWrapper);

		try {
			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("SocietyName","Vividh Karyakari Society");

			JasperPrint print = JasperFillManager.fillReport(report, params,beanDataSource);

			savingAccountreportblob = JasperExportManager.exportReportToPdf(print);

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return savingAccountreportblob;
	}



}
