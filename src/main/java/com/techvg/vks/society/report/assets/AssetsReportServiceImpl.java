package com.techvg.vks.society.report.assets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.AssetsRegistration;
import com.techvg.vks.society.repository.AssetsRegistrationRepository;
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
public class AssetsReportServiceImpl implements AssetsReportService{

	private static final String FILEPATH_REPORT = "/AssetsRegister.jrxml";

	private final AssetsRegistrationRepository assetsRegistrationRepository;
	private final AssetsReportMapper assetsReportMapper;
	@Override
	public ResponseEntity<?> getAssetsReport(String fromDate,String toDate) {
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_PDF).body(creatingAssetsReport(fromDate,toDate));

	}
	

	private byte[] creatingAssetsReport(String fromDate,String toDate) {
		File resource = null;
		byte[] reportblob = null;

		try {
			resource = new ClassPathResource(FILEPATH_REPORT).getFile();

		} catch (IOException e) {
			throw new NotFoundException("File Not Found...");
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		LocalDate fromdate1 = LocalDate.parse(fromDate, formatter);
					
		Instant instant = fromdate1.atStartOfDay(ZoneId.systemDefault()).toInstant();
		Date fromDate1 = Date.from(instant);
		
		LocalDate todate1 = LocalDate.parse(toDate, formatter);
						
			Instant instant1 = todate1.atStartOfDay(ZoneId.systemDefault()).toInstant();
			Date toDate1 = Date.from(instant1);
		List<AssetsRegistration> assetsRegistrationList = assetsRegistrationRepository.findByDateFromtoTo(fromDate1, toDate1);

		List<AssetsRegistration> assetsRegistrationList1 = new ArrayList<>();
		for(int i=1;i<assetsRegistrationList.size();i++)
		{
			assetsRegistrationList1.add(assetsRegistrationList.get(i));
			
		}
	
		List<AssetsReportWrapper> assetsListWrapper = this.assetsReportMapper.mapAllDataList(assetsRegistrationList1);

		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(assetsListWrapper);

		try {
			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String, Object> params = new HashMap<>(); 	
			params.put("SocietyName", "Vividh Karyakari Society");
			
			params.put("openingQuantity", assetsRegistrationList.get(0).getBalanceQuantity());
			params.put("openingValue", assetsRegistrationList.get(0).getBalanceValue());
			
			params.put("date", assetsRegistrationList.get(0).getDate());
			params.put("date1", assetsRegistrationList.get(assetsRegistrationList.size()-1).getDate());

			params.put("billNo", assetsRegistrationList.get(0).getBillNo());
			params.put("billNo1", assetsRegistrationList.get(assetsRegistrationList.size()-1).getBillNo());
			
			if(assetsRegistrationList.get(0).getTransactionType().equalsIgnoreCase("purchase")) {
			params.put("purchaseQty", assetsRegistrationList.get(0).getQuantity());
			params.put("purchaseQty1", assetsRegistrationList.get(assetsRegistrationList.size()-1).getQuantity());
			params.put("purchaseVal", assetsRegistrationList.get(0).getCost());
			params.put("purchaseVal1", assetsRegistrationList.get(assetsRegistrationList.size()-1).getCost());
  
			}else if(assetsRegistrationList.get(0).getTransactionType().equalsIgnoreCase("sales")) {
				params.put("salesQty", assetsRegistrationList.get(0).getQuantity());
				params.put("salesQty1", assetsRegistrationList.get(assetsRegistrationList.size()-1).getQuantity());
				params.put("salesVal", assetsRegistrationList.get(0).getCost());
				params.put("salesVal1", assetsRegistrationList.get(assetsRegistrationList.size()-1).getCost());
	  
				}
			
			params.put("closingQuantity", assetsRegistrationList.get(assetsRegistrationList.size()-1).getBalanceQuantity());
			params.put("closingValue", assetsRegistrationList.get(assetsRegistrationList.size()-1).getBalanceValue());
		
			JasperPrint print = JasperFillManager.fillReport(report, params, beanDataSource);

			reportblob = JasperExportManager.exportReportToPdf(print);

		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
		return reportblob;
	}

}
