package com.techvg.vks.deposit.reports.fdreport;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.techvg.vks.deposit.domain.Deposit;
import com.techvg.vks.deposit.repository.DepositRepository;
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
public class FdReportServiceImpl implements FdReportService{
	

	private final FdReportMapper fdReportMapper;
	private final DepositRepository depositRepository;
	
	
	private static final String FILEPATH_FD_SLIPS_REPORT = "/FD_SLIPS_REPORT.jrxml";
	
	
	@Override
	public byte[] getFdlistReport() {

		List<Deposit> deposits = depositRepository.findByDepositAccountDepositTypeAccountType("Fixed");

		System.out.println("depositAccount"+deposits);
		List<FdReportWrapper> fdReportWrapperList=this.fdReportMapper.mapAllFdDataList(deposits);

		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(fdReportWrapperList);
		return (getfdListReportforAll(beanDataSource));
	}

	
	private byte[] getfdListReportforAll(JRBeanCollectionDataSource beanDataSource) {
		File resource = null;
		byte[] fdreportblob = null;
		
		try {
			resource = new ClassPathResource(FILEPATH_FD_SLIPS_REPORT).getFile();
			log.info("file got for defaulter list report = "+resource.getName());
			
		}
		catch(IOException e)
		{ 
			throw new NotFoundException("File Not Found...");
		}

		try {
			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("SocietyName","Vividh Karyakari Society");

			JasperPrint print = JasperFillManager.fillReport(report, params,beanDataSource);

			fdreportblob = JasperExportManager.exportReportToPdf(print);

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return fdreportblob;
	}

	@Override
	public byte[] getFdlistReport(Long depositId) {
		Deposit deposit = depositRepository.findById(depositId).orElseThrow(NotFoundException::new);
		
		System.out.println("depositAccount"+deposit);
		FdReportWrapper fdReportWrapper=this.fdReportMapper.mapAllFdData(deposit);
		ArrayList<FdReportWrapper> dataBean = new ArrayList();
		dataBean.add(fdReportWrapper);
		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(dataBean);
		return (getfdListReportforAll(beanDataSource));
	}
	}


