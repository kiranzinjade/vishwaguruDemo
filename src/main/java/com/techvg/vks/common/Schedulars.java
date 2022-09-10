package com.techvg.vks.common;

import com.techvg.vks.config.LoanConstants;
import com.techvg.vks.config.NotificationConstants;
import com.techvg.vks.deposit.domain.Deposit;
import com.techvg.vks.deposit.repository.DepositRepository;
import com.techvg.vks.deposit.service.DepositService;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.repository.LoanDetailsRepository;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.domain.NotificationDetails;
import com.techvg.vks.membership.repository.MemberRepository;
import com.techvg.vks.membership.repository.NotificationDetailsRepository;
import com.techvg.vks.society.domain.Notification;
import com.techvg.vks.society.domain.NpaSetting;
import com.techvg.vks.society.repository.DepositAccountRepository;
import com.techvg.vks.society.repository.NotificationRepository;
import com.techvg.vks.society.repository.NpaSettingRepository;
import com.techvg.vks.trading.domain.Product;
import com.techvg.vks.trading.domain.Stock;
import com.techvg.vks.trading.domain.StockRegister;
import com.techvg.vks.trading.repository.ProductRepository;
import com.techvg.vks.trading.repository.StockRegisterRepository;
import com.techvg.vks.trading.repository.StockRepository;
import com.techvg.vks.trading.service.StockService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@Service
@RequiredArgsConstructor
@Slf4j
public class Schedulars {
	private final LoanDetailsRepository loanDetailsRepository;
	private final NpaSettingRepository npaSettingRepository;
	private final StockRepository stockRepository;
	private final StockRegisterRepository stockRegisterRepository;
	
	@Value("${stockSchedular}")
     String cronExpression;

	private final DepositRepository depositRepository;
	private final  NotificationDetailsRepository notificationDetailsRepository; 
	private final  MemberRepository memberRepository;
	private final  NotificationRepository notificationRepository;

	@Scheduled(cron = "0 5 02 ? * *")
	public void maturitySocietyInvestment() throws ParseException {
		List<Deposit> deposit = depositRepository.findByReinvestmentStatus(true);
		for (Deposit depositObj : deposit) {
            Date currentDate=new Date();
			Date beforeMaturityDate = depositObj.getMaturityDate();
			beforeMaturityDate.setDate(beforeMaturityDate.getDate() - 2);
			 SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");	
			 String strCurrentDate = sdformat.format(currentDate);
			 String strBeforeMaturityDate = sdformat.format(beforeMaturityDate);
		      Date d1 = sdformat.parse(strCurrentDate);
		      Date d2 = sdformat.parse(strBeforeMaturityDate);
		      System.out.println("Maturity Before two date"+d2);
		      if(d1.compareTo(d2) == 0) {
		    	  NotificationDetails  notificationDetails=new  NotificationDetails();
		    	  Member member = memberRepository.findById(depositObj.getMember().getId()).orElseThrow(NotFoundException::new);
		  		  Notification notification =notificationRepository.findByNotificationType(NotificationConstants.MATURITY_NOTIFICATION).orElseThrow(NotFoundException::new);
		  		  notificationDetails.setMember(member);
				  notificationDetails.setNotification(notification);
				  notificationDetails=notificationDetailsRepository.save(notificationDetails);		    
		       }
		}
	}


private final DepositAccountRepository depositAccountRepository;
private final DepositService depositService; 
@Scheduled(cron = "0 15 23 ? * *")
public void maturityReInvestment() throws ParseException {
	List<Deposit> deposit = depositRepository.findByReinvestmentStatus(true);
	for (Deposit depositObj : deposit) {
        Date currentDate=new Date();
		Date maturityDate = depositObj.getMaturityDate();
		
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");	
		 String strCurrentDate = sdformat.format(currentDate);
		 String strMaturityDate = sdformat.format(maturityDate);
	      Date d1 = sdformat.parse(strCurrentDate);
	      Date d2 = sdformat.parse(strMaturityDate);
	      if(d1.compareTo(d2) == 0) {
	    	  System.out.println("ReInvestment");
	    	  depositService.renewDeposit(depositObj.getId());
	      } 
  }
}
private final StockService stockService;

@Scheduled(cron="${stockSchedular}")
public void updateOpeningClosing() throws ParseException {
	Long productId = null;
	List<StockRegister> stockList=stockRegisterRepository.findAll();
	for (StockRegister stock : stockList) {
		 productId=stock.getProduct().getId();
	}   
	stockService.updateOpeningClosing(productId);
System.out.println("hiiii");
}



@Scheduled(cron = "0 53 9 ? * *")
//EX:-> 0 1 12 1 * ?	Fire at 12:1 AM on the 1th day of every month
//@Scheduled(cron = "0 1 12 1 * ?")
public void loanDefaulterMonthly() throws ParseException{
	List<LoanDetails> loanDetails = loanDetailsRepository.findByLoanPlannedCloserMonthly(LoanConstants.LOAN_Active);
	loanDetails.forEach(objLoan ->{
			String loanClassfication = "";
		 Date startDate = objLoan.getLoanPlannedClosureDate();	 
		 Calendar calendar = Calendar.getInstance();
         calendar.add(Calendar.MONTH, 0);
         calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
         Date monthLastDay = calendar.getTime();
         SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
         String endDateMonthStr = sdformat.format(monthLastDay);	
         Date endDate1 = null;
		try {
			endDate1 = sdformat.parse(endDateMonthStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date endDate =endDate1;
		 int days = DateConverter.dayDiff(startDate, endDate);
		 double year = (double) days / 365;
		 List<NpaSetting> npaSetting = npaSettingRepository.findByisDeleted(false);
		 for (NpaSetting npaSettingObj : npaSetting) {
			 if (year > npaSettingObj.getDurationStart() && year <= npaSettingObj.getDurationEnd()) {
				 loanClassfication = npaSettingObj.getClassification();
				 System.out.println("Loan Classfication1"+loanClassfication);
			 }
		 }
		 System.out.println("Loan Classfication"+loanClassfication);

		 if(!objLoan.getLoanClassification().equals(loanClassfication)) {
			   
			 
			  NotificationDetails  notificationDetails=new  NotificationDetails();
	    	  Member member = memberRepository.findById(objLoan.getMember().getId()).orElseThrow(NotFoundException::new);
	  		  Notification notification =notificationRepository.findByNotificationType(NotificationConstants.LOAN_DEFAULTER_NOTIFICATION_MONTHLY).orElseThrow(NotFoundException::new);
	  		  notificationDetails.setNotificationStatus(NotificationConstants.NOTIFICATION_STATUS);
	  		  notificationDetails.setMember(member);
			  notificationDetails.setNotification(notification);
			  notificationDetails=notificationDetailsRepository.save(notificationDetails);
		 }
	});
		
}








}
