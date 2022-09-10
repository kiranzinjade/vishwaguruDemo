package com.techvg.vks.deposit.reports.SavingsAccountLedger;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.techvg.vks.deposit.domain.DepositLedger;
import com.techvg.vks.deposit.domain.SavingAccount;
import com.techvg.vks.deposit.domain.SavingInterest;
import com.techvg.vks.deposit.repository.DepositLedgerRepository;
import com.techvg.vks.deposit.repository.SavingAccountRepository;
import com.techvg.vks.deposit.repository.SavingInterestRepository;
import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
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

public class SavingAccountReportServiceImpl implements SavingAccountReportService {

	private final SavingAccountRepository savingAccountRepository;
	private final SavingInterestRepository savingInterestRepository;
	private final DepositLedgerRepository depositLedgerRepository;
	private static final String FILEPATH_REPORT = "/SavingAccount.jrxml";

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public ResponseEntity<?> getSavingAccountListReport(Long accountNo) {
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_PDF)
				.body(getSavingAccountReport(accountNo));
	}

	private byte[] getSavingAccountReport(Long accountNo) {
		File resource = null;
		byte[] reportblob = null;
		Connection conn = null;

		try {
			resource = new ClassPathResource(FILEPATH_REPORT).getFile();

		} catch (IOException e) {
			throw new NotFoundException("File Not Found...");
		}

		Optional<SavingAccount> savingAccount = savingAccountRepository.findByAccountNo(accountNo);
        if(savingAccount.isPresent()) {
		List<SavingInterest> savingInterest = savingInterestRepository.findByAccountNo(accountNo);

		DepositLedger depositLedger = depositLedgerRepository.findByLastRecord(accountNo);

		ArrayList<SavingAccount> savingAccountList = new ArrayList<>();
		savingAccountList.add(savingAccount.get());

		try {
			conn = jdbcTemplate.getDataSource().getConnection();

			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));

			Map<String, Object> params = new HashMap<>();

			params.put("accountNo", savingAccount.get().getAccountNo());
			savingAccount.get().member.getNominees().forEach(action -> {

				params.put("nomineeFirstName",
						action.getFirstName() + " " + action.getMiddleName() + " " + action.getLastName());
				params.put("nomineeRelation", action.getNomineeRelation());
				params.put("dob", action.getDob());

				params.put("guardian", action.getGuardianName() + "," + action.getParentPermanentAddr());
			});
			savingAccount.get().member.getHouse().forEach(action -> {
				if (action.getAddressType().contentEquals("PERMANENT")) {
				params.put("address", action.getAddressLine1() + ", " + action.getAddressLine2() + ", "
						+ action.getCity() + ", " + action.getState() + ", PIN - " + action.getPincode());
				}
			});
			params.put("date", depositLedger.getDate());
			params.put("lastDate", depositLedger.getDepositDate());

//			params.put("monthlyBalance", savingInterest.getMonthlyBalance());
//			params.put("interest", savingInterest.getMonthlyInterest());
//			

			JasperPrint print = JasperFillManager.fillReport(report, params, conn);

			reportblob = JasperExportManager.exportReportToPdf(print);

		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
		return reportblob;
	}else {
        throw new NotFoundException("Account number not found: " +accountNo );

	}
	}

}
