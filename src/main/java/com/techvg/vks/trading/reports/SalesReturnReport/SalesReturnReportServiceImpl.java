package com.techvg.vks.trading.reports.SalesReturnReport;

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
import com.techvg.vks.trading.domain.PurchaseRegister;
import com.techvg.vks.trading.domain.SalesReturnDetails;
import com.techvg.vks.trading.reports.PurchaseRegisterReport.PurchaseRegisterReportMapper;
import com.techvg.vks.trading.reports.PurchaseRegisterReport.PurchaseRegisterReportServiceImpl;
import com.techvg.vks.trading.reports.PurchaseRegisterReport.PurchaseRegisterReportWrapper;
import com.techvg.vks.trading.repository.PurchaseRegisterRepository;
import com.techvg.vks.trading.repository.SalesReturnDetailsRepository;

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
public class SalesReturnReportServiceImpl  implements SalesReturnReportService {

	private final SalesReturnReportMapper salesReturnReportMapper;
	private final SalesReturnDetailsRepository salesReturnDetailsRepository;

	private static final String FILEPATH_SALES_RETURN_REPORT = "/salesReturnReport.jrxml";

	@Override
	public byte[] getSalesReturnReport() {
		File resource = null;
		byte[] salesreturnblob = null;

		try {
			resource = new ClassPathResource(FILEPATH_SALES_RETURN_REPORT).getFile();
			log.info("file got for defaulter list report = " + resource.getName());

		} catch (IOException e) {
			throw new NotFoundException("File Not Found...");
		}

		SalesReturnDetails salesReturn = salesReturnDetailsRepository.findTopByOrderByIdDesc();
			
		List<SalesReturnDetails> salesReturnDetails = new ArrayList<>();

		salesReturnDetails = salesReturnDetailsRepository.findBySalesReturnId(salesReturn.getSalesReturn().getId());

		List<SalesReturnReportWrapper> salesReturnWrapper = this.salesReturnReportMapper.mapAllSalesReturnDataList(salesReturnDetails);

		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(salesReturnWrapper);

		try {
			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String, Object> params = new HashMap<>();
			params.put("SocietyName", "Vividh Karyakari Society");
			
			JasperPrint print = JasperFillManager.fillReport(report, params, beanDataSource);

			salesreturnblob = JasperExportManager.exportReportToPdf(print);

		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
		return salesreturnblob;
	}

}

