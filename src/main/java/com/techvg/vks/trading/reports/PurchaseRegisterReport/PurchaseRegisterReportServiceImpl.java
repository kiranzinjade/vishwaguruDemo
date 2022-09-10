package com.techvg.vks.trading.reports.PurchaseRegisterReport;

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
import com.techvg.vks.trading.repository.PurchaseRegisterRepository;

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
public class PurchaseRegisterReportServiceImpl implements PurchaseRegisterReportService {
	
	private final PurchaseRegisterReportMapper purchaseRegisterReportMapper;
	private final PurchaseRegisterRepository purchaseRegisterRepository;

	private static final String FILEPATH_PURCHASE_REGISTER_REPORT = "/purchaseRegisterReport.jrxml";


	@Override
	public byte[] getPurchaseRegisterReport(Long purchaseOrderId) {
		File resource = null;
		byte[] purchasereportblob = null;

		try {
			resource = new ClassPathResource(FILEPATH_PURCHASE_REGISTER_REPORT).getFile();
			log.info("file got for defaulter list report = " + resource.getName());

		} catch (IOException e) {
			throw new NotFoundException("File Not Found...");
		}

		List<PurchaseRegister> purchaseRegister = new ArrayList<>();

		purchaseRegister = purchaseRegisterRepository.findByPurchaseOrderId(purchaseOrderId);

		
		List<PurchaseRegisterReportWrapper> purchaseRegisterWrapper = this.purchaseRegisterReportMapper.mapAllPurchaseDataList(purchaseRegister);

		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(purchaseRegisterWrapper);

		try {
			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String, Object> params = new HashMap<>();
			params.put("SocietyName", "Vividh Karyakari Society");
			
			JasperPrint print = JasperFillManager.fillReport(report, params, beanDataSource);

			purchasereportblob = JasperExportManager.exportReportToPdf(print);

		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
		return purchasereportblob;
	}

}
