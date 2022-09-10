package com.techvg.vks.membership.reports.MemberNotice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.techvg.vks.common.NumberToWordConversionService;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.model.LoanInterestDetails;
import com.techvg.vks.loan.repository.LoanDetailsRepository;
import com.techvg.vks.loan.service.LoanDetailsService;
import com.techvg.vks.membership.domain.Nominee;
import com.techvg.vks.membership.repository.NomineeRepository;
import com.techvg.vks.society.domain.Society;
import com.techvg.vks.society.repository.SocietyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberNoticeServiceImpl implements MemberNoticeService {

	private static final String FILEPATH_MEMBER_NOTICE_REPORT = "/Notice.jrxml";
    private final SocietyRepository societyRepository;
    private final LoanDetailsRepository loanDetailsRepository;
    private final NumberToWordConversionService numberToWordConversionService;
    private final NomineeRepository nomineeRepository;
    private final LoanDetailsService loanDetailsService;
	@Override
	public byte[] getMemberNoticeReport(Long memberId) {
		File resource = null;
		byte[] memberNoticereportblob = null;
		
		try {
			resource = new ClassPathResource(FILEPATH_MEMBER_NOTICE_REPORT).getFile();
			log.info("file got for Member Notice report = "+resource.getName());
			
		}
		catch(IOException e)
		{ 
			throw new NotFoundException("File Not Found...");
		}

		LoanDetails loanDetails1=loanDetailsRepository.findByMemberIdAndStatus(memberId);

		List<Society> society=societyRepository.findAll();
		System.out.println("society"+loanDetails1.getLoanAmt());
		System.out.println("memberId"+memberId);
	
		List<Nominee> nomlist = nomineeRepository.findByMemberId(memberId);
				
		int IntLoanAmount = (int)Math.round(loanDetails1.getLoanAmt());
		
		LoanInterestDetails loanInterestDetails=loanDetailsService.calculateLoanInterest(loanDetails1.getId(),new Date());
		
		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(null);

		

		try {
			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("SocietyName",society.get(0).getSocietyName());
			params.put("SocietyAddress",society.get(0).getAddress());
			params.put("RegistrationNo",society.get(0).getRegistrationNumber());
			params.put("SocietyPanNo",society.get(0).getPanNumber());   
			params.put("SocietyState",society.get(0).getState());
			params.put("SocietyDist",society.get(0).getDistrict());
			params.put("SocietyTahsil",society.get(0).getTahsil());
			params.put("SocietyVillage",society.get(0).getVillage());
			params.put("SocietyPincode",society.get(0).getPinCode());
			params.put("Date",new Date());
			params.put("LoanAcountNo",loanDetails1.getLoanAccountNo());
			params.put("LoanDate",loanDetails1.getLoanEffectiveDate());
			params.put("LoanAmount",loanDetails1.getLoanAmt());
			
			params.put("LoanAmountWord", numberToWordConversionService.convert(Integer.parseInt(""+IntLoanAmount)));
			params.put("LoanPurpose",loanDetails1.getLoanProduct().getProductName());
			params.put("ResolutionNo",loanDetails1.getResolutionNo().toString());
			
			loanDetails1.member.getHouse().forEach(action->{
				if(action.getAddressType().equals("PERMANENT")) {
					params.put("MemberAddress",action.getAddressLine1()+" "+action.getAddressLine2()+" "+action.getCity()+" "+action.getDistrict()+" "+action.getState());
				}

			});
			
			params.put("LoanDuration",loanDetails1.getLoanProduct().getDuration());
			params.put("ClosingPrinciple", (double)Math.round(loanInterestDetails.getTotalOutstanding()));
			params.put("Intetest",(double)Math.round(loanInterestDetails.getTotalLoanInterest()));
			params.put("Penalty",(double)Math.round(loanInterestDetails.getPenaltyAmount()));
			
			params.put("MemberName",loanDetails1.member.getLastName()+" "+loanDetails1.member.getFirstName()+" "+loanDetails1.member.getMiddleName());
			params.put("Nominee1",nomlist.get(0).getLastName()+" "+nomlist.get(0).getFirstName()+" "+nomlist.get(0).getMiddleName());
			params.put("Nominee2",nomlist.get(1).getLastName()+" "+nomlist.get(1).getFirstName()+" "+nomlist.get(1).getMiddleName());
			
			JasperPrint print = JasperFillManager.fillReport(report, params,beanDataSource);

			memberNoticereportblob = JasperExportManager.exportReportToPdf(print);

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return memberNoticereportblob;
	}

	

	
}
