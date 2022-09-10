package com.techvg.vks.society.reports.BorrowingLedgerReport;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.techvg.vks.deposit.domain.SavingAccount;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.BorrowingLedger;
import com.techvg.vks.society.domain.BorrowingLedgerTransaction;
import com.techvg.vks.society.repository.BorrowingLedgerRepository;
import com.techvg.vks.society.repository.BorrowingLedgerTransactionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
@RequiredArgsConstructor
@Slf4j
public class BorrowingLedgerReportServiceImpl implements BorrowingLedgerReportService{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	

	private final BorrowingLedgerReportMapper borrowingLedgerReportMapper;
	private final BorrowingLedgerTransactionRepository borrowingLedgerTransactionRepository;
	private final BorrowingLedgerRepository borrowingLedgerRepository;
	private static final String FILEPATH_BORROWING_LEDGER_REPORT = "/Borrowing_Ledger.jrxml";



	@Override
	public byte[] getBorrowingLedgerListReportforAll(Long bankId) throws SQLException ,IOException,JRException{
		File resource = null;
		byte[] borrowingreportblob = null;
		Connection conn=null;
		
		try {
			resource = new ClassPathResource(FILEPATH_BORROWING_LEDGER_REPORT).getFile();
			log.info("file got for defaulter list report = "+resource.getName());
			
		}
		catch(IOException e)
		{ 
			throw new NotFoundException("File Not Found...");
		}
   
//		List<BorrowingLedgerTransaction> borrowingLedgerList = borrowingLedgerTransactionRepository.findAll();
		Optional<BorrowingLedger> borrowingLedger = borrowingLedgerRepository.findByBankId(bankId);
	
		ArrayList<BorrowingLedger> borrowingLedgerList = new ArrayList<>();
		borrowingLedgerList.add(borrowingLedger.get());
//		System.out.println("print " +borrowingLedgerList);

//		List<BorrowingLedgerReportWrapper> borrowingLedgerReportWrapper=this.borrowingLedgerReportMapper.mapAllBorrowingLedgerDataList(borrowingLedgerList);
		
		conn = jdbcTemplate.getDataSource().getConnection();

		try {
			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("SocietyName","Vividh Karyakari Society");
			params.put("bankId", borrowingLedger.get().getBank().getId());
			JasperPrint print = JasperFillManager.fillReport(report, params,conn);

			borrowingreportblob = JasperExportManager.exportReportToPdf(print);

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return borrowingreportblob;
	}

}
