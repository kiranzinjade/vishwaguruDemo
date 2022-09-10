package com.techvg.vks.trading.reports.PurchaseReturnReport;

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
import com.techvg.vks.trading.domain.PurchaseReturnDetails;
import com.techvg.vks.trading.domain.SalesReturnDetails;
import com.techvg.vks.trading.reports.SalesReturnReport.SalesReturnReportMapper;
import com.techvg.vks.trading.reports.SalesReturnReport.SalesReturnReportServiceImpl;
import com.techvg.vks.trading.reports.SalesReturnReport.SalesReturnReportWrapper;
import com.techvg.vks.trading.repository.PurchaseReturnDetailsRepository;
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
public class PurchaseReturnReportServiceImpl implements PurchaseReturnReportService {
	
	private final PurchaseReturnReportMapper purchaseReturnReportMapper;
	private final PurchaseReturnDetailsRepository purchaseReturnDetailsRepository;

	private static final String FILEPATH_PURCHASE_RETURN_REPORT = "/purchaseReturnReport.jrxml";


	@Override
	public byte[] getPurchaseReturnReport() {
		File resource = null;
		byte[] purchasereturnblob = null;

		try {
			resource = new ClassPathResource(FILEPATH_PURCHASE_RETURN_REPORT).getFile();
			log.info("file got for defaulter list report = " + resource.getName());

		} catch (IOException e) {
			throw new NotFoundException("File Not Found...");
		}

		PurchaseReturnDetails purchaseReturn = purchaseReturnDetailsRepository.findTopByOrderByIdDesc();
				
		List<PurchaseReturnDetails> purchaseReturnDetails = new ArrayList<>();

		purchaseReturnDetails = purchaseReturnDetailsRepository.findByPurchaseReturnId(purchaseReturn.getPurchaseReturn().getId());

		List<PurchaseReturnReportWrapper> purchaseReturnWrapper = this.purchaseReturnReportMapper.mapAllPurchaseReturnDataList(purchaseReturnDetails);

		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(purchaseReturnWrapper);

		try {
			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String, Object> params = new HashMap<>();
			params.put("SocietyName", "Vividh Karyakari Society");
		
			JasperPrint print = JasperFillManager.fillReport(report, params, beanDataSource);

			purchasereturnblob = JasperExportManager.exportReportToPdf(print);

		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
		return purchasereturnblob;
	}

}


