package com.techvg.vks.loan.reports.ShortTermMemberList;

import com.techvg.vks.config.LoanConstants;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.repository.LoanDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

public class MemberListServiceImpl implements MemberListService {
	
	private final MemberListMapper memberListMapper;
	private final LoanDetailsRepository loanDetailsRepository;
	
	private static final String FILEPATH_REPORT = "/MemberListOfShortTermReapyment.jrxml"; 
	
	@Override
	public ResponseEntity<?> getShortTermMemberListReport(String loanStatus) {
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_PDF).body(getShortTermMemberListReportForAllMember(loanStatus));
	}

	@Override
	public byte[] getShortTermMemberListReportForAllMember(String loanStatus) {
		File resource = null;
		byte[] reportblob = null;
		
		try {
			resource = new ClassPathResource(FILEPATH_REPORT).getFile();
			log.info("file got for Member list report = "+resource.getName());
			
		}
		catch(IOException e)
		{ 
			throw new NotFoundException("File Not Found...");
		}

		List<LoanDetails> loanDetailslist = loanDetailsRepository.findByLoanTypeAndLoanStatus(LoanConstants.SHORT_TERM_LOAN,loanStatus);

		List<MemberListWrapper> memberListWrapper=this.memberListMapper.mapAllDataList(loanDetailslist);
		
		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(memberListWrapper);

		try {
			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String,Object> params = new HashMap<>();
			params.put("SocietyName","Vividh Karyakari Society");

			JasperPrint print = JasperFillManager.fillReport(report, params,beanDataSource);

			reportblob = JasperExportManager.exportReportToPdf(print);

		}
		catch(Exception e){
			log.error(e.getLocalizedMessage());
		}
		return reportblob;
	}


}
