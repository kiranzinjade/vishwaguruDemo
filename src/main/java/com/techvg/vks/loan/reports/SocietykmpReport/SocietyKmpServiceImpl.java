package com.techvg.vks.loan.reports.SocietykmpReport;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.CropLoanDemand;
import com.techvg.vks.loan.repository.CropLoanDemandRepository;
import com.techvg.vks.loan.repository.KMPMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocietyKmpServiceImpl implements SocietyKmpService {
	private final CropLoanDemandRepository cropLoanDemandRepository;
	private final SocietyKmpMapper societyKmpMapper;
	private final KMPMemberRepository KMPMemberRepository;
	private static final String FILEPATH_KMP_REPORT = "/kmp - Copy.jrxml";

	@Override
	public ResponseEntity<?> generateAllSocietyKmpReports(List<Long> cropLoanDemandIdList) {
		cropLoanDemandIdList.forEach(action ->{
			generateSocietyKmpReport(action);
		});
		return null;
	}

	private ResponseEntity<?> getSocietyKmpReport(Long cropLoanDemandId) {
		generateSocietyKmpReport(cropLoanDemandId);
		return null;
	}

	@Override
	public ResponseEntity<?> generateSocietyKmpReport(Long cropLoanDemandId) {
		CropLoanDemand croploanDemand=cropLoanDemandRepository.findByCropLoanDemandId(cropLoanDemandId);
		List<SocietyKmpReportWrapper> wrapperList = societyKmpMapper.mapAllKMPDataList(croploanDemand.getLoanDemand());
		creatingSocietyKmpReport(wrapperList);
		croploanDemand.setSocietyKmpStatus(true);
		cropLoanDemandRepository.save(croploanDemand);
		return null;
	}


	@Override
	public byte[] creatingSocietyKmpReport(List<SocietyKmpReportWrapper> wrapperList) {
		File resource = null;
		byte[] kmpreportblob = null;

		try {
			resource = new ClassPathResource(FILEPATH_KMP_REPORT).getFile();
			log.info("file got for Kmp Report = "+resource.getName());

		}
		catch(IOException e)
		{
			throw new NotFoundException("File Not Found...");
		}


		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(wrapperList);

		try {
			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String,Object> params = new HashMap<>();
			params.put("SocietyName","Vividh Karyakari Society");

			JasperPrint print = JasperFillManager.fillReport(report,params,beanDataSource);
			kmpreportblob = JasperExportManager.exportReportToPdf(print);

		}
		catch(Exception e){
			log.error(e.getLocalizedMessage());
		}

		return kmpreportblob;
	}


}


