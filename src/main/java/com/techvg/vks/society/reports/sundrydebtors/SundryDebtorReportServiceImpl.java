package com.techvg.vks.society.reports.sundrydebtors;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.trading.domain.SundryDebtor;
import com.techvg.vks.trading.domain.SundryDebtorTransaction;
import com.techvg.vks.trading.repository.SundryDebtorRepository;
import com.techvg.vks.trading.repository.SundryDebtorTransactionRepository;

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
public class SundryDebtorReportServiceImpl implements SundryDebtorReportService{
	
	private final SundryDebtorReportMapper sundryDebtorReportMapper;
	private final SundryDebtorTransactionRepository sundryDebtorTransactionRepository;
	private static final String FILEPATH_REPORT = "/SundryDebtor.jrxml";

	
	@Override
	public ResponseEntity<?> getSundryDebtorsReport(Long sundryDebtorId) {
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_PDF)
				.body(getSundryDebtorReportforMember(sundryDebtorId));
	}

	private byte[] getSundryDebtorReportforMember(Long sundryDebtorId) {
		File resource = null;
		byte[] reportblob = null;

		try {
			resource = new ClassPathResource(FILEPATH_REPORT).getFile();

		} catch (IOException e) {
			throw new NotFoundException("File Not Found...");
		}

		List<SundryDebtorTransaction> sundryDebtor = sundryDebtorTransactionRepository.findBysundryDebtorId(sundryDebtorId);

		List<SundryDebtorReportWrapper> sundryDebtorReportWrapper = this.sundryDebtorReportMapper.mapAllDataList(sundryDebtor);

		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(sundryDebtorReportWrapper);

		try {
			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String, Object> params = new HashMap<>();

			JasperPrint print = JasperFillManager.fillReport(report, params, beanDataSource);

			reportblob = JasperExportManager.exportReportToPdf(print);

		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
		return reportblob;
	}
}
