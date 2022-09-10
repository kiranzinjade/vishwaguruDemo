package com.techvg.vks.membership.reports.MemberwiseShareReport;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.membership.service.MemberService;
import com.techvg.vks.share.repository.SharesAllocationRepository;
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
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberwiseShareReportServiceImpl implements MemberwiseShareReportService {
	

	private final MemberService memberService;
	private final SharesAllocationRepository sharesAllocationRepository;

	private static final String FILEPATH_MEMBERWISESHARE_REPORT = "/memberwiseshare.jrxml";
	
	@Override
	public byte[] getMemberwiseShareListReport() {
		return (getmemberwiseShareListReportForAllMember());
		
	}
	@Override
	public byte[] getmemberwiseShareListReportForAllMember() {
		File resource = null;
		byte[] memberwiseSharereportblob = null;
		
		try {
			resource = new ClassPathResource(FILEPATH_MEMBERWISESHARE_REPORT).getFile();
			log.info("file got for defaulter list report = "+resource.getName());
			
		}
		catch(IOException e)
		{ 
			throw new NotFoundException("File Not Found...");
		}


		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(memberService.listMemberShares());

		try {
			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("SocietyName","Vividh Karyakari Society");

			JasperPrint print = JasperFillManager.fillReport(report, params,beanDataSource);

			memberwiseSharereportblob = JasperExportManager.exportReportToPdf(print);

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return memberwiseSharereportblob;
	}


}
