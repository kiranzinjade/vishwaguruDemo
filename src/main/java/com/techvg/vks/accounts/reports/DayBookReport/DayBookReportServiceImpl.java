package com.techvg.vks.accounts.reports.DayBookReport;

import com.techvg.vks.accounts.domain.DayBook;
import com.techvg.vks.accounts.repository.DayBookRepository;
import com.techvg.vks.config.AccountConstants;
import com.techvg.vks.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DayBookReportServiceImpl implements DayBookReportService  {
	
	
	
		@Autowired
		JdbcTemplate jdbcTemplate;
		
		private final DayBookRepository dayBookRepository;
		private final DayBookReportMapper  dayBookReportMapper;
		private static final String FILEPATH_DAYBOOK_REPORT = "/DayBookReport.jrxml";

		@Override
		public byte[] generateDayBookReport(String transDate) throws SQLException, IOException, JRException {
			File resource = null;
			byte[] dayBookReportblob = null;
			Connection conn = null;
			double totalTranfCredit = 0.0;
			double totalTranfDebit = 0.0;
			double totalCashCredit=0.0;
			double totalCashDebit = 0.0;
			double totalCr=0.0;
			double totalDb=0.0;
			double Balance=0.0;
			double totalCr1=0.0;

			try {
				resource = new ClassPathResource(FILEPATH_DAYBOOK_REPORT).getFile();
				log.info("file got for day book list report = " + resource.getName());

			} catch (IOException e) {
				throw new NotFoundException("File Not Found...");
			}

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
			LocalDate transDate1 = LocalDate.parse(transDate, formatter);

			Instant instant = transDate1.atStartOfDay(ZoneId.systemDefault()).toInstant();
			Date transDate2 = Date.from(instant);

			double openingBalance =0.0;
			List<DayBook> dayBookList = dayBookRepository.findAllByTransDate(transDate2);

			DayBook day = dayBookRepository.findByLastRecord(transDate2);
			if(day !=null)
				openingBalance =day.getBalance();

			DayBook dayBook1 = dayBookRepository.findByLastRecordOfTheDay(transDate2);

			for (DayBook dayBook : dayBookList) {
			
				if (dayBook.getTransType().equals(AccountConstants.CREDIT)) {
					totalCashCredit = totalCashCredit + dayBook.getCreditCashAmt();
					totalTranfCredit=totalTranfCredit + dayBook.getCreditTransferAmt();
					

				} else {
					totalCashDebit = totalCashDebit + dayBook.getDebitCashAmt();
					totalTranfDebit=totalTranfDebit + dayBook.getDebitTransferAmt();
					
				}
			}
			totalCashDebit+=openingBalance;
			totalDb=totalCashDebit+totalTranfDebit;
			totalCr=totalCashCredit+totalTranfCredit;
			
			Balance=totalDb-totalCr;
			totalCashCredit+=Balance;
			totalCr1=totalCr+Balance;

			try {
				conn = jdbcTemplate.getDataSource().getConnection();
				JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
				Map<String, Object> params = new HashMap<>();
				params.put("SocietyName", "Vividh Karyakari Society");
				params.put("transDate", transDate2);
				params.put("totalCashCredit", totalCashCredit);
				params.put("totalCashDebit", totalCashDebit);
				params.put("balance",Balance );
				params.put("lastBalance", openingBalance);
				params.put("creditTotal", totalCr1);
				params.put("debitTotal", totalDb);
				params.put("totalTranfCredit", totalTranfCredit);
				params.put("totalTranfDebit", totalTranfDebit);

				JasperPrint print = JasperFillManager.fillReport(report, params,conn);

				dayBookReportblob = JasperExportManager.exportReportToPdf(print);

			} catch (Exception e) {
				log.error(e.getLocalizedMessage());
			}
			return dayBookReportblob;
		}

	}


