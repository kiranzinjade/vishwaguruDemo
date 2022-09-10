package com.techvg.vks.membership.reports.MemberListReport;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.repository.MemberRepository;
import com.techvg.vks.society.repository.AgmAttendanceRepository;
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
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberReportServiceImpl implements MemberReportService {
	

private final MemberRepository memberRepo;
private final MemberReportMapper memberReportMapper;
private final AgmAttendanceRepository agmAttendanceRepository;

private static final String FILEPATH_MEMBER_REPORT = "/MEMBER_REPORT.jrxml"; 


@Override
public ResponseEntity<?> getMemberListReport(String memberType) {
	
	switch(memberType.toUpperCase())
	{ 
	case ALL:
		
		log.info("Member Type received (service) successfully = "+ALL);
		
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_PDF).body(getmemberListReportForAllMember());
	
	default:
	throw new NotFoundException("Specified Token is Not Found");

	}
		
}


@Override
public byte[] getmemberListReportForAllMember() {
	File resource = null;
	byte[] memberreportblob = null;
	
	try {
		resource = new ClassPathResource(FILEPATH_MEMBER_REPORT).getFile();
		log.info("file got for member report = "+resource.getName());
		
	}
	catch(IOException e)
	{ 
		throw new NotFoundException("File Not Found...");
	}

	Long[] agmAttendancelist = agmAttendanceRepository.findByAttendanceList();
	Long[] productlist = {Long.valueOf(681)};
	Long[] transactionlist =  {Long.valueOf(681)};
	
	HashSet<Long> set = new HashSet<>();
     
    set.addAll(Arrays.asList(productlist));
    set.addAll(Arrays.asList(transactionlist));
    set.addAll(Arrays.asList(agmAttendancelist)); 
    Long[] union = {};
    union = set.toArray(union);
    List<Member> memberReportWrapper1= new ArrayList<>();
	List<MemberReportWrapper> memberReportWrapper=null;
	for (int i = 0; i < union.length; i++){
	    Member member = memberRepo.findById(union[i]).orElseThrow(NotFoundException::new);
	
		String m = member.getStatus();
		String n = "A";
		if(m.equals(n))
		{
			memberReportWrapper1.add(member);
			memberReportWrapper=this.memberReportMapper.mapAllMemberDataList(memberReportWrapper1);
		}
	}
	
	JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(memberReportWrapper);

	try {
		JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
		Map<String,Object> params = new HashMap<>();
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
