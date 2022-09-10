package com.techvg.vks.deposit.reports.MonthlyInterestFD;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.techvg.vks.config.DepositConstants;
import com.techvg.vks.deposit.domain.Deposit;
import com.techvg.vks.deposit.domain.SavingAccount;
import com.techvg.vks.deposit.reports.fdreport.FdReportMapper;
import com.techvg.vks.deposit.reports.fdreport.FdReportServiceImpl;
import com.techvg.vks.deposit.reports.fdreport.FdReportWrapper;
import com.techvg.vks.deposit.repository.DepositRepository;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.membership.domain.Member;

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
public class MonthlyInterestFDServiceImpl implements MonthlyInterestFDService {
	
	private final MonthlyInterestFDMapper monthlyInterestFDMapper;
	private final DepositRepository depositRepository;
	private static final String FILEPATH_FD_MONTHLY_INTEREST_REGISTER = "/monthlyinterestpaymentRegister.jrxml";
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	@Override
	public ResponseEntity<?> getFDMember(Long memberId) {
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_PDF)
				.body(getFDMemberReport(memberId));
	}
	
	private byte[] getFDMemberReport(Long memberId) {
		File resource = null;
		byte[] reportblob = null;
		Connection conn = null;

		try {
			resource = new ClassPathResource(FILEPATH_FD_MONTHLY_INTEREST_REGISTER).getFile();

		} catch (IOException e) {
			
			throw new NotFoundException("File Not Found...");
		}

		
		try {

			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String, Object> params = new HashMap<>();
			
			Deposit deposit = depositRepository.findByMemberIdAndDepositAccountDepositTypeAccountType(memberId,DepositConstants.DEPOSIT_ACC_TYPE_FD);
	
			int duration=deposit.depositAccount.getFdDurationDays();
			double roi=deposit.depositAccount.getInterest();
			double depositAmt=deposit.getDepositAmount();
			double perdayInterest= (depositAmt * roi / 100 / 365);
			double paidInterest;
			Integer month=(int) (duration/30) ;
			int diff=0;
			Date interestDepositDate=new Date();//temporary date //set as per society
			Calendar cal = Calendar.getInstance();
			cal.setTime(interestDepositDate); 
			cal.add(Calendar.DATE,30);
			params.put("accountNo",deposit.getAccountNo());
			params.put("receiptNo",deposit.getReceiptNo());
			params.put("depositDate",deposit.getDepositDate());
			params.put("maturityDate",deposit.getMaturityDate());
			params.put("name",deposit.member.getFirstName()+" "+deposit.member.getMiddleName()+" "+deposit.member.getLastName());
			  
			List< MonthlyInterestFDWrapper> monthlyInterestFDWrapperList=new ArrayList<MonthlyInterestFDWrapper>();
			MonthlyInterestFDWrapper wrapper=new MonthlyInterestFDWrapper();
			  
			  if(duration<=15) {
					paidInterest=perdayInterest*duration;
					wrapper.setInterestEarned(paidInterest);
					monthlyInterestFDWrapperList.add(wrapper);
					}else{
				    			for(int i=0; i < month; i++){
				    			    
				    					if(diff >= 0)
				    					{
				    						MonthlyInterestFDWrapper wrapper1=new MonthlyInterestFDWrapper();
				    						diff=duration-30;
				    						paidInterest=perdayInterest*30;
				    						wrapper1.setInterestEarned(paidInterest);
				    						wrapper1.setDepositDate(cal.getTime());
				    						cal.add(Calendar.DATE, 30);
					    					monthlyInterestFDWrapperList.add(wrapper1);
					    					duration=diff;

				    					        if(duration==15)
				   
				    					        {
				    							  MonthlyInterestFDWrapper wrapper2=new MonthlyInterestFDWrapper();
				    							  paidInterest=perdayInterest*15;
				    					          wrapper2.setInterestEarned(paidInterest);
				    							  wrapper2.setDepositDate(cal.getTime());
						    					  cal.add(Calendar.DATE, 30);
							    				  monthlyInterestFDWrapperList.add(wrapper2);
							    				}
				    					 }	 
				    				}
	                        }
				    				    
		
			JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(monthlyInterestFDWrapperList);
			JasperPrint print = JasperFillManager.fillReport(report, params, beanDataSource);
			reportblob = JasperExportManager.exportReportToPdf(print);

		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
		return reportblob;
	}	
	

}


	