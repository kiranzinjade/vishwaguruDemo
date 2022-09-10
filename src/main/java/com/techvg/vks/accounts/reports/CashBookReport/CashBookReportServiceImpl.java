package com.techvg.vks.accounts.reports.CashBookReport;

import com.techvg.vks.accounts.domain.CashBook;
import com.techvg.vks.accounts.domain.LedgerAccounts;
import com.techvg.vks.accounts.repository.CashBookRepository;
import com.techvg.vks.accounts.repository.LedgerAccountsRepository;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CashBookReportServiceImpl implements CashBookReportService {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private final CashBookRepository cashBookRepository;
	private final LedgerAccountsRepository ledgerAccRepo;
	
	private static final String FILEPATH_CASHBOOK_REPORT = "/CashBookReport1.jrxml";

	@Override
	public byte[] generateCashBookReport(String transDate){
		File resource = null;
		byte[] cashBookReportblob = null;
		Connection conn = null;
		double sumCredit = 0.0;
		double sumDebit = 0.0;

		try {
			resource = new ClassPathResource(FILEPATH_CASHBOOK_REPORT).getFile();
			log.info("file got for cash book list report = " + resource.getName());

		} catch (IOException e) {
			throw new NotFoundException("File Not Found...");
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		LocalDate transDate1 = LocalDate.parse(transDate, formatter);

		Instant instant = transDate1.atStartOfDay(ZoneId.systemDefault()).toInstant();
		Date transDate2 = Date.from(instant);

		double openingBalance =0.0;
		List<CashBook> cashBookList = cashBookRepository.findByCashBookRecord(transDate2);

		CashBook cash = cashBookRepository.findByLastRecord(transDate2);
		if(cash !=null) {
			openingBalance = cash.getBalance();
		}else{
			LedgerAccounts cashLedger = ledgerAccRepo.findByAccHeadCode(AccountConstants.CASH_ON_HAND);
			openingBalance = cashLedger.getAccBalance();
		}

		CashBook cashBook1 = cashBookRepository.findByLastRecordOfTheDay(transDate2);

		ArrayList<CashBook> creditList = new ArrayList<>();
		ArrayList<CashBook> debitList = new ArrayList<>();

		for (CashBook cashBook : cashBookList) {
			if (cashBook.getDebitAmt() == 0) {
				creditList.add(cashBook);
				sumCredit = sumCredit + cashBook.getCreditAmt();

			} else {
				debitList.add(cashBook);
				sumDebit = sumDebit + cashBook.getDebitAmt();
			}
		}

		Double totalCredit = sumCredit + cashBook1.getBalance();
		Double totalDebit = sumDebit + openingBalance;

		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String, Object> params = new HashMap<>();
			params.put("SocietyName", "Vividh Karyakari Society");
			params.put("transDate", transDate2);
			params.put("creditSum", sumCredit);
			params.put("debitSum", sumDebit);
			params.put("balance", cashBook1.getBalance());
			params.put("lastBalance", openingBalance);
			params.put("creditTotal", totalCredit);
			params.put("debitTotal", totalDebit);

			JasperPrint print = JasperFillManager.fillReport(report, params,conn);

			cashBookReportblob = JasperExportManager.exportReportToPdf(print);

		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
		return cashBookReportblob;
	}

}
