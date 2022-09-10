package com.techvg.vks.membership.reports.VoterListReport;

import com.techvg.vks.exceptions.NotFoundException;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@RequiredArgsConstructor
@Slf4j
public class VoterReportServiceImpl implements VoterReportService {

private final MemberRepository memberRepo;
private final VoterReportMapper voterReportMapper;

private static final String FILEPATH_VOTER_REPORT = "/VOTER_REPORT.jrxml"; 

@Override
public ResponseEntity<?> getVoterListReport(String memberType) {
	
	switch(memberType.toUpperCase())
	{ 
	case ALL:
		log.info("Member Type received (service) successfully = "+ALL);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_PDF).body(getvoterListReportForAllMember());
	
	default:
	throw new NotFoundException("Specified Token is Not Found");

	}
}

@Override
public byte[] getvoterListReportForAllMember() {
	File resource = null;
	byte[] memberreportblob = null;
	
	try {
		resource = new ClassPathResource(FILEPATH_VOTER_REPORT).getFile();
		log.info("file got for member report = "+resource.getName());
		
	}
	catch(IOException e)
	{ 
		throw new NotFoundException("File Not Found...");
	}
	List<Member> memberlist = memberRepo.findAll();
	List<Member> voterReportWrapper1= new ArrayList<Member>();
	List<VoterReportWrapper> voterReportWrapper=null;
	for(int i=0; i<memberlist.size();i++)
	{
		java.util.Date startDate = new java.util.Date();
		java.util.Date endDate = memberlist.get(i).getApplicationDate();

		long diff = startDate.getTime() - endDate.getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);
		int diffDays1=(int)diffDays ; 
		
		String m = memberlist.get(i).getStatus();
		String n = "A";
		
		if((m.equals(n))&&(diffDays1 >= 730)) 	
		{			 
			voterReportWrapper1.add(memberlist.get(i));
			voterReportWrapper =this.voterReportMapper.mapAllVoterDataList(voterReportWrapper1);
		}			
		
	}
	JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(voterReportWrapper);

	try {
		JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("SocietyName","Vividh Karyakari Society");

		JasperPrint print = JasperFillManager.fillReport(report, params,beanDataSource);

		memberreportblob = JasperExportManager.exportReportToPdf(print);

	}
	catch(Exception e){
		e.printStackTrace();
	}
	
	return memberreportblob;
	}



}
