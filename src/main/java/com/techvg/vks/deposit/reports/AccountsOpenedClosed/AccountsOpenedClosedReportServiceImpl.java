package com.techvg.vks.deposit.reports.AccountsOpenedClosed;

import com.techvg.vks.config.DepositConstants;
import com.techvg.vks.deposit.repository.DepositRepository;
import com.techvg.vks.deposit.repository.SavingAccountRepository;
import com.techvg.vks.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j

public class AccountsOpenedClosedReportServiceImpl implements AccountsOpenedClosedReportService{

	private final DepositRepository depositRepository;
	private final AccountsOpenedClosedReportMapper accountsOpenedClosedReportMapper;
	private final SavingsAccountMapper savingsAccountMapper;
	private final SavingAccountRepository savingAccountRepository;
	private static final String FILEPATH_REPORT = "/AccountsOpenedClosed.jrxml";

	@Autowired
	JdbcTemplate jdbcTemplate;


	@Override
	public ResponseEntity<?> getAccountListReport(String accountType) {
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_PDF)
				.body(getAccountList(accountType));
	}

	private byte[] getAccountList(String accountType) {
		File resource = null;
		byte[] reportblob = null;

		try {
			resource = new ClassPathResource(FILEPATH_REPORT).getFile();

		} catch (IOException e) {
			throw new NotFoundException("File Not Found...");
		}

		List<AccountsOpenedClosedReportWrapper> accountsOpenedClosedReportWrapper = null;

		if(accountType.equalsIgnoreCase(DepositConstants.DEPOSIT_ACC_TYPE_SAVINGS)){
			accountsOpenedClosedReportWrapper = savingsAccountMapper.mapAllSavingsDataList(savingAccountRepository.findListBySavingsAccountType());
		}else if(accountType.equalsIgnoreCase(DepositConstants.DEPOSIT_ACC_TYPE_FD)){
			accountsOpenedClosedReportWrapper = accountsOpenedClosedReportMapper.mapAllDepositDataList(depositRepository.findListByFDAccountType());
		}else if(accountType.equalsIgnoreCase(DepositConstants.DEPOSIT_ACC_TYPE_RD)){
			accountsOpenedClosedReportWrapper = accountsOpenedClosedReportMapper.mapAllDepositDataList(depositRepository.findListByRDAccountType());
		}

        System.out.println("accountsOpenedClosedReportWrapper"+accountsOpenedClosedReportWrapper.toString());
		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(accountsOpenedClosedReportWrapper);

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
