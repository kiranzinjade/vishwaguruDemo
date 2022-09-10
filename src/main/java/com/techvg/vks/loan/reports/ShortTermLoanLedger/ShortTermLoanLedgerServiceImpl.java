package com.techvg.vks.loan.reports.ShortTermLoanLedger;

import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.Disbursement;
import com.techvg.vks.loan.repository.DisbursementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
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
public class ShortTermLoanLedgerServiceImpl implements ShortTermLoanLedgerService {

	private final ShortTermLoanLedgerMapper shortTermLoanMapper;
	private final DisbursementRepository disbursementRepository;

	private static final String FILEPATH_SHORT_TERM_LOAN_LEDGER_REPORT = "/SHORT_TERM_LOAN_LEDGER.jrxml";

	@Override
	public byte[] getShortTermLoanListReport() {
		return (getShortTermLoanListReport());

	}

	@Override
	public byte[] getShortTermLoanListReport(Long loanId) {
		File resource = null;
		byte[] shortTermLoanreportblob = null;

		try {
			resource = new ClassPathResource(FILEPATH_SHORT_TERM_LOAN_LEDGER_REPORT).getFile();
			log.info("file got for defaulter list report = " + resource.getName());

		} catch (IOException e) {
			throw new NotFoundException("File Not Found...");
		}

		List<Disbursement> disbursement = disbursementRepository.findByLoanId(loanId);
		if (disbursement.get(0).getLoanType().equals("short")) {
			List<ShortTermLoanLedgerWrapper> shortTermLoanWrapper = this.shortTermLoanMapper
					.mapAllShortTermLoanDataList(disbursement);
			

			JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(shortTermLoanWrapper);

			try {
				JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
				Map<String, Object> params = new HashMap<>();
				params.put("SocietyName", "Vividh Karyakari Society");

				JasperPrint print = JasperFillManager.fillReport(report, params, beanDataSource);

				shortTermLoanreportblob = JasperExportManager.exportReportToPdf(print);

			} catch (Exception e) {
				log.error(e.getLocalizedMessage());
			}
		} else {
			throw new AlreadyExitsException(" Add Appropriate details: ");
		}

		return shortTermLoanreportblob;
	}

}