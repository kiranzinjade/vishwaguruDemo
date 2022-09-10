package com.techvg.vks.batchjobs;

import com.techvg.vks.config.LoanConstants;
import com.techvg.vks.loan.domain.Amortization;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.repository.AmortizationRepository;
import com.techvg.vks.loan.repository.LoanDetailsRepository;
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
public class LoanEMIDue {

    private final AmortizationRepository repo;
    private final LoanDetailsRepository loanRepo;

    @Scheduled(cron = "00 49 23 ? * *")
    @Transactional
    public void updateAmortization() {
        Date currentDate = new Date();
        List<LoanDetails> loanDetailsList = loanRepo.findMidLongLoanMembers();
        for(LoanDetails loanDetails : loanDetailsList){
            Amortization amortizationDue = repo.findByLoanIdAndStatus( loanDetails.getId() , LoanConstants.DUE);
            if(amortizationDue.getInstallmentDate().before(currentDate)){
                amortizationDue.setPaymentStatus(LoanConstants.OVERDUE);
                repo.save(amortizationDue);
            }
        }
    }
}
