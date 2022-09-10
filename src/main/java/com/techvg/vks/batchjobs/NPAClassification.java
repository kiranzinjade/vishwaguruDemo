package com.techvg.vks.batchjobs;

import com.techvg.vks.common.DateConverter;
import com.techvg.vks.config.LoanConstants;
import com.techvg.vks.config.NotificationConstants;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.repository.LoanDetailsRepository;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.domain.NotificationDetails;
import com.techvg.vks.membership.repository.MemberRepository;
import com.techvg.vks.membership.repository.NotificationDetailsRepository;
import com.techvg.vks.society.domain.Notification;
import com.techvg.vks.society.domain.NpaSetting;
import com.techvg.vks.society.repository.NotificationRepository;
import com.techvg.vks.society.repository.NpaSettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NPAClassification {

    private final LoanDetailsRepository loanDetailsRepository;
    private final NpaSettingRepository npaSettingRepository;
    private final NotificationDetailsRepository notificationDetailsRepository;
    private final MemberRepository memberRepository;
    private final NotificationRepository notificationRepository;

    @Scheduled(cron = "00 49 23 ? * *")
    @Transactional
    public void updateLoanClassification() {
        List<LoanDetails> loanDetails = loanDetailsRepository.findByLoanStatus(LoanConstants.LOAN_Active);

        loanDetails.forEach(objLoan ->{
            String loanClassification = "";
            Date startDate = objLoan.getLoanPlannedClosureDate();
            Date endDate = new Date();
            int days = DateConverter.dayDiff(startDate, endDate);
            double year = (double) days / 365;

            List<NpaSetting> npaSetting = npaSettingRepository.findByisDeleted(false);
            for (NpaSetting npaSettingObj : npaSetting) {

                if (year > npaSettingObj.getDurationStart() && year <= npaSettingObj.getDurationEnd()) {
                    loanClassification = npaSettingObj.getClassification();
                    System.out.println("loan Classification--IN--->"+loanClassification);
                }
            }

            if(!objLoan.getLoanClassification().equals(loanClassification)) {
                objLoan.setLoanClassification(loanClassification);
                loanDetailsRepository.save(objLoan);

                NotificationDetails notificationDetails=new  NotificationDetails();
                Member member = memberRepository.findById(objLoan.getMember().getId()).orElseThrow(NotFoundException::new);
                Notification notification =notificationRepository.findByNotificationType(NotificationConstants.LOAN_DEFAULTER_NOTIFICATION).orElseThrow(NotFoundException::new);
                notificationDetails.setNotificationStatus(NotificationConstants.NOTIFICATION_STATUS);
                notificationDetails.setMember(member);
                notificationDetails.setNotification(notification);
                notificationDetails=notificationDetailsRepository.save(notificationDetails);
            }
        });
    }

}
