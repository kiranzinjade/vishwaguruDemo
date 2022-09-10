package com.techvg.vks.loan.reports.LoanDisbursementRegister;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.repository.LoanDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanDisbursementRegisterServiceImpl implements LoanDisbursementRegisterService {

	private final LoanDisbursementRegisterMapper loanDisbursementRegisterMapper;
	private final LoanDetailsRepository loanDetailsRepository;

	private static final String FILEPATH_LOAN_DISBURSEMENT_REGISTER_REPORT = "/Loan_Disbursement_Register.jrxml";

	@Override
	public byte[] getLoanDisbursementReport(String loanType, String loanAmtCriteria) {
		return getLoanDisbursementReport(loanType, loanAmtCriteria);
	}

	@Override
	public byte[] getLoanDisursementRegisterReport(String loanType, String loanAmtCriteria) {
		File resource = null;
		byte[] disbusrementreportblob = null;

		try {
			resource = new ClassPathResource(FILEPATH_LOAN_DISBURSEMENT_REGISTER_REPORT).getFile();
			log.info("file got for defaulter list report = " + resource.getName());

		} catch (IOException e) {
			throw new NotFoundException("File Not Found...");
		}

		List<LoanDetails> loanDetail = new ArrayList<>();

		if (loanAmtCriteria.equals("loan_criteria_below_3lac")) {
			loanDetail = loanDetailsRepository.findByLoanAmountLessThan(loanType);
		} else if (loanAmtCriteria.equals("loan_criteria_above_3lac")) {
			loanDetail = loanDetailsRepository.findByLoanAmountGreaterThan(loanType);

		}

		List<LoanDisbursementRegisterWrapper> disbursementListWrapper = this.loanDisbursementRegisterMapper
				.mapAllDisbursementDataList(loanDetail);

		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(disbursementListWrapper);

		try {
			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String, Object> params = new HashMap<>();
			params.put("SocietyName", "Vividh Karyakari Society");

			JasperPrint print = JasperFillManager.fillReport(report, params, beanDataSource);

			disbusrementreportblob = JasperExportManager.exportReportToPdf(print);

		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
		return disbusrementreportblob;
	}

}
