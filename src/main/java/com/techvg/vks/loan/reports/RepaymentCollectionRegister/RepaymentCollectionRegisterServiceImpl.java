package com.techvg.vks.loan.reports.RepaymentCollectionRegister;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.repository.LoanDetailsRepository;
import com.techvg.vks.society.domain.Society;
import com.techvg.vks.society.repository.SocietyRepository;

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
public class RepaymentCollectionRegisterServiceImpl implements RepaymentCollectionRegisterService {

	private final RepaymentCollectionRegisterMapper  repaymentCollectionRegisterMapper;
	private final LoanDetailsRepository loanDetailsRepository;
	private final SocietyRepository societyRepository;
	String societyName;
	
	
	private static final String FILEPATH_DEFAULTER_REPORT = "/Repayment_Collection_Register.jrxml"; 
	
	
	@Override
	public byte[] getRepaymentCollectionRegisterReport(String loanType, String loanClassfication) {
		return getRepaymentCollectionReport(loanType,loanClassfication);
	}

	@Override
	public byte[] getRepaymentCollectionReport(String loanType,String loanClassfication) {
		File resource = null;
		byte[] repaymentreportblob = null;
		
		try {
			resource = new ClassPathResource(FILEPATH_DEFAULTER_REPORT).getFile();
			log.info("file got for defaulter list report = "+resource.getName());
			
		}
		catch(IOException e)
		{ 
			throw new NotFoundException("File Not Found...");
		}
		List<LoanDetails> loanDetailslist = loanDetailsRepository.findRepaymentList(loanType,loanClassfication);
		
		List<Society> society=societyRepository.findAll();

		List<RepaymentCollectionRegisterWrapper> repaymentListWrapper=this.repaymentCollectionRegisterMapper.mapAllRegisterDataList(loanDetailslist);
		
		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(repaymentListWrapper);

		try {
			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String,Object> params = new HashMap<>();
			society.forEach(action->{
				societyName=action.getSocietyName();
			});
			params.put("SocietyName",societyName);

			JasperPrint print = JasperFillManager.fillReport(report, params,beanDataSource);

			repaymentreportblob = JasperExportManager.exportReportToPdf(print);

		}
		catch(Exception e){
			log.error(e.getLocalizedMessage());
		}
		return repaymentreportblob;
	}

	}


