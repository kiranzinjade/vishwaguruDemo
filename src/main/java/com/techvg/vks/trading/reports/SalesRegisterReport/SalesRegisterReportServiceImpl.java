package com.techvg.vks.trading.reports.SalesRegisterReport;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.trading.domain.SalesRegister;
import com.techvg.vks.trading.repository.SalesRegisterRepository;

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
public class SalesRegisterReportServiceImpl implements SalesRegisterReportService {

	private final SalesRegisterReportMapper salesRegisterReportMapper;
	private final SalesRegisterRepository salesRegisterRepository;

	private static final String FILEPATH_SALES_REGISTER_REPORT = "/salesRegisterReport.jrxml";


	@Override
	public byte[] getSalesRegisterReport(Long salesOrderId) {
		File resource = null;
		byte[] salesreportblob = null;

		try {
			resource = new ClassPathResource(FILEPATH_SALES_REGISTER_REPORT).getFile();
			log.info("file got for defaulter list report = " + resource.getName());

		} catch (IOException e) {
			throw new NotFoundException("File Not Found...");
		}
		
		//SalesRegister sales = salesRegisterRepository.findTopByOrderByIdDesc();
		
		List<SalesRegister> salesRegister = new ArrayList<>();

		salesRegister = salesRegisterRepository.findBySalesOrderId(salesOrderId);

		List<SalesRegisterReportWrapper> salesRegisterWrapper = this.salesRegisterReportMapper.mapAllSalesDataList(salesRegister);

		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(salesRegisterWrapper);

		try {
			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String, Object> params = new HashMap<>();
			params.put("SocietyName", "Vividh Karyakari Society");
			

			JasperPrint print = JasperFillManager.fillReport(report, params, beanDataSource);

			salesreportblob = JasperExportManager.exportReportToPdf(print);

		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
		return salesreportblob;
	}

}
