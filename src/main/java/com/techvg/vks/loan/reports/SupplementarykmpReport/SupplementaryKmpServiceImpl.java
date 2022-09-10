package com.techvg.vks.loan.reports.SupplementarykmpReport;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.LoanDemand;
import com.techvg.vks.loan.repository.LoanDemandRepository;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.repository.MemberRepository;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupplementaryKmpServiceImpl implements SupplementaryKmpService {
private final MemberRepository memberRepository;
private final SupplementaryKmpMapper  supplementaryKmpMapper;
private final LoanDemandRepository loanDemandRepository;
private static final String FILEPATH_KMP_REPORT = "/SupplementaryKmp.jrxml"; 

@Override
public ResponseEntity<?> getSupplementaryKmpReport(Long loanDemandId) {
	log.info("Laon Deamand Id received (service) successfully = "+loanDemandId);
	return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_PDF).body(CreatingSupplementaryKmpReport(loanDemandId));

}

@Override
public byte[] CreatingSupplementaryKmpReport(Long loanDemandId) {
	File resource = null;
	byte[] kmpreportblob = null;
	
	try {
		resource = new ClassPathResource(FILEPATH_KMP_REPORT).getFile();
		log.info("file got for Kmp Report = "+resource.getName());
		
	}
	catch(IOException e)
	{ 
		throw new NotFoundException("File Not Found...");
	}
	
	Optional<LoanDemand> loanDemand=loanDemandRepository.findById(loanDemandId);
	Optional<Member> member =memberRepository.findById(loanDemand.get().member.getId());
	
	List<SupplementaryKmpReportWrapper> kmpReportWrapper=null;

			
	JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(kmpReportWrapper);

	try {
		JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
		Map<String,Object> params = new HashMap<>();
		
		params.put("SocietyName","Pune Madhyavarti Sahkari Bank Vividh Karyakari Society");
		params.put("MemberName",member.get().getFirstName()+" "+member.get().getFatherName()+" "+member.get().getLastName());
		params.put("MemberId",member.get().getId());
		JasperPrint print = JasperFillManager.fillReport(report,params,beanDataSource);
		kmpreportblob = JasperExportManager.exportReportToPdf(print);
		
	}
	catch(Exception e){
		log.error(e.getLocalizedMessage());
	}
	
	return kmpreportblob;
	}

}

