package com.techvg.vks.accounts.reports.generalledgerreport;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.techvg.vks.accounts.domain.GeneralLedger;
import com.techvg.vks.accounts.mapper.GeneralLedgerMapper;
import com.techvg.vks.accounts.model.GeneralLedgerDto;
import com.techvg.vks.accounts.repository.GeneralLedgerRepository;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.reports.loandisbursement.LoanDisbursementReportWrapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeneralLedgerReportServiceImpl implements GeneralLedgerReportService {

	private static final String FILEPATH = "/GeneralLedger.jrxml";
	private final GeneralLedgerRepository generalLedgerRepository;
	private final GeneralLedgerReportMapper generalLedgerReportMapper;
	private final GeneralLedgerMapper generalLedgerMapper;
	@Autowired
	JdbcTemplate jdbcTemplate;

//	@Override
//	public ResponseEntity<?> getGeneralLedger(Long ledgerAccountId) {
//
//		List<GeneralLedger> generalLedger = generalLedgerRepository.findByLedgerAccountsId(ledgerAccountId);
//		List<GeneralLedgerReportWrapper> generalLedgerReportWrapperlist = this.generalLedgerReportMapper
//				.mapAllList(generalLedger);
//
//		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_PDF)
//				.body(getGeneralledgerReport(generalLedgerReportWrapperlist));
//
//	}

//	@Override
//	public byte[] getAllGeneralLedger() {
//		File resource = null;
//		byte[] reportblob = null;
//		Connection conn = null;
//
//		try {
//			resource = new ClassPathResource(FILEPATH).getFile();
//
//		} catch (IOException e) {
//			throw new NotFoundException("File Not Found...");
//		}
//
//		List<GeneralLedger> generalLedgerList = generalLedgerRepository.findAll();
//		List<GeneralLedgerReportWrapper> generalLedgerReportWrapperlist = new ArrayList<>();
//		generalLedgerReportWrapperlist = this.generalLedgerReportMapper.mapAllList(generalLedgerList);
//		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(generalLedgerReportWrapperlist);
//		try {
//			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("SocietyName", "Vividh Karyakari Society");
////			System.out.println("general list----------------------------------"+generalLedgerList);
////			for(int i=0;i<generalLedgerList.size();i++)
////			{
////				generalLedgerList.add(generalLedger.get(i));
////			//	params.put("ledgerAccountId", generalLedgerList.get(i).getLedgerAccounts().getId());
////
////				params.put("accountHeadCode", generalLedgerList.get(i).getLedgerAccounts().getAccHeadCode());
////
////			}
//
//			JasperPrint print = JasperFillManager.fillReport(report, params, beanDataSource);
//
//			reportblob = JasperExportManager.exportReportToPdf(print);
//			// conn.close();
//
//		} catch (Exception e) {
//			// conn.close();
//			e.printStackTrace();
//		}
//
//		return reportblob;
//	}

//	@Override
//	public byte[] getGeneralledgerReport(List<GeneralLedgerReportWrapper> generalLedgerReportWrapperlist) {
//		File resource = null;
//		byte[] reportblob = null;
//		Connection conn = null;
//
//		try {
//			resource = new ClassPathResource(FILEPATH).getFile();
//
//		} catch (IOException e) {
//			throw new NotFoundException("File Not Found...");
//		}
//		// conn = jdbcTemplate.getDataSource().getConnection();
//		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(generalLedgerReportWrapperlist);
//		try {
//			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("SocietyName", "Vividh Karyakari Society");
////			for(int i=0;i<generalLedger.size();i++)
////			{
////				generalLedgerList.add(generalLedger.get(i));
////				params.put("ledgerAccountId", generalLedger.get(i).getLedgerAccounts().getId());
////
////				params.put("accountHeadCode", generalLedger.get(i).getLedgerAccounts().getAccHeadCode());
////
////			}
//
//			JasperPrint print = JasperFillManager.fillReport(report, params, beanDataSource);
//
//			reportblob = JasperExportManager.exportReportToPdf(print);
//			// conn.close();
//
//		} catch (Exception e) {
//			// conn.close();
//			e.printStackTrace();
//		}
//		return reportblob;
//
//	}

	@Override
	public  List<ArrayList<GeneralLedgerDto>> getGeneralLedgerList() {
		List<GeneralLedger> generalLedgerList = generalLedgerRepository.findAll();
		List<GeneralLedgerDto> dtoList=generalLedgerMapper.domainToDtoList(generalLedgerList);
		HashMap<Long, ArrayList<GeneralLedgerDto>> map1 = new HashMap<>();
		ArrayList<GeneralLedgerDto> list = new ArrayList<>();

		for (GeneralLedgerDto generalLedger : dtoList) {

			Long ledgerId = generalLedger.getLedgerAccountId();

			if (map1.containsKey(ledgerId)) {
				ArrayList<GeneralLedgerDto> listTemp = map1.get(ledgerId);

				listTemp.add(generalLedger);

				map1.put(ledgerId, listTemp);

			} else {
				list = new ArrayList<GeneralLedgerDto>();
				list.add(generalLedger);
				map1.put(ledgerId, list);

			}
		}
		Collection<ArrayList<GeneralLedgerDto>> values = map1.values(); 
		List<ArrayList<GeneralLedgerDto>> listOfValues = new ArrayList<ArrayList<GeneralLedgerDto>>(values); 
	
		return listOfValues;
	}

}
