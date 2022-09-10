package com.techvg.vks.loan.service;

import com.techvg.vks.config.LoanConstants;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.domain.NPAData;
import com.techvg.vks.loan.mapper.NPAMapper;
import com.techvg.vks.loan.model.LoanInterestDetails;
import com.techvg.vks.loan.model.NPADataDto;
import com.techvg.vks.loan.repository.LoanDetailsRepository;
import com.techvg.vks.loan.repository.NPADataRepository;
import com.techvg.vks.society.model.NpaSettingDto;
import com.techvg.vks.society.service.NpaSettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NPADataServiceImpl implements NPADataService {

    private final NPADataRepository repo;
    private final NPAMapper mapper;
    private final NpaSettingService npaSettingService;
    private final LoanDetailsRepository loanDetailsRepo;
    private final LoanDetailsService loanDetailsService;

    @Override
    public List<NPADataDto> getLatestNPAData() {
        return null;
    }

    @Override
    public NPADataDto getLatestNPAData(Date npaDate) {
        return null;
    }


    @Override
    public NPADataDto storeNPAData(NPADataDto npaDataDto) {
        return mapper.toDto(repo.save(mapper.toDomain(npaDataDto)));
    }

    @Override
    public List<NPADataDto> generateNPAData() {

        List<NPADataDto> npaDataDtoList = new ArrayList<>();
        List<LoanDetails> loanDetailsList = loanDetailsRepo.findAllActiveLoans();

        NPADataDto shortTermNpaData = new NPADataDto();
        NPADataDto midTermNpaData = new NPADataDto();
        NPADataDto longTermNpaData = new NPADataDto();

        shortTermNpaData.setLoanType(LoanConstants.SHORT_TERM_LOAN);
        shortTermNpaData.setNpaDate(new Date());
        midTermNpaData.setLoanType(LoanConstants.MID_TERM_LOAN);
        midTermNpaData.setNpaDate(new Date());
        longTermNpaData.setLoanType(LoanConstants.LONG_TERM_LOAN);
        longTermNpaData.setNpaDate(new Date());

        for(LoanDetails loanDetails:loanDetailsList){
            LoanInterestDetails interestDetails = loanDetailsService.calculateLoanInterest(loanDetails, new Date());
            if(loanDetails.getLoanType().equalsIgnoreCase(LoanConstants.SHORT_TERM_LOAN)){
                shortTermNpaData = populateNPAData(loanDetails.getLoanClassification(), shortTermNpaData, interestDetails);
            }
            if(loanDetails.getLoanType().equalsIgnoreCase(LoanConstants.MID_TERM_LOAN)){
                midTermNpaData = populateNPAData(loanDetails.getLoanClassification(), midTermNpaData, interestDetails);
            }
            if(loanDetails.getLoanType().equalsIgnoreCase(LoanConstants.LONG_TERM_LOAN)){
                longTermNpaData = populateNPAData(loanDetails.getLoanClassification(), longTermNpaData, interestDetails);
            }
        }

        npaDataDtoList.add(shortTermNpaData);
        npaDataDtoList.add(midTermNpaData);
        npaDataDtoList.add(longTermNpaData);

        populateNPAProvisioning(npaDataDtoList);
        return npaDataDtoList;
    }

    private NPADataDto populateNPAData(String classification, NPADataDto npaDataDto, LoanInterestDetails interestDetails){

        if(classification.equalsIgnoreCase(LoanConstants.STANDARD)){
            npaDataDto.setStandardCnt( npaDataDto.getStandardCnt() + 1);
            npaDataDto.setStandardAmt(npaDataDto.getStandardAmt() + interestDetails.getTotalOutstanding());
        }
        if(classification.equalsIgnoreCase(LoanConstants.SUB_STANDARD)){
            npaDataDto.setSubstandardCnt( npaDataDto.getSubstandardCnt() + 1);
            npaDataDto.setSubstandardAmt(npaDataDto.getSubstandardAmt() + interestDetails.getTotalOutstanding());
        }
        if(classification.equalsIgnoreCase(LoanConstants.DOUBTFUL_1)){
            npaDataDto.setDoubtful1Cnt( npaDataDto.getDoubtful1Cnt() + 1);
            npaDataDto.setDoubtful1Amt(npaDataDto.getDoubtful1Amt() + interestDetails.getTotalOutstanding());
        }
        if(classification.equalsIgnoreCase(LoanConstants.DOUBTFUL_2)){
            npaDataDto.setDoubtful2Cnt( npaDataDto.getDoubtful2Cnt() + 1);
            npaDataDto.setDoubtful2Amt(npaDataDto.getDoubtful2Amt() + interestDetails.getTotalOutstanding());
        }
        if(classification.equalsIgnoreCase(LoanConstants.DOUBTFUL_3)){
            npaDataDto.setDoubtful3Cnt( npaDataDto.getDoubtful3Cnt() + 1);
            npaDataDto.setDoubtful3Amt(npaDataDto.getDoubtful3Amt() + interestDetails.getTotalOutstanding());
        }
        if(classification.equalsIgnoreCase(LoanConstants.LOSS)){
            npaDataDto.setLossCnt( npaDataDto.getLossCnt() + 1);
            npaDataDto.setLossAmt(npaDataDto.getLossAmt() + interestDetails.getTotalOutstanding());
        }
        return npaDataDto;
    }

    private void populateNPAProvisioning(List<NPADataDto> npaDataDtoList){
        List<NpaSettingDto> npaSettingDtoList = npaSettingService.listAllNpa();
        npaSettingDtoList.forEach(setting->{
            if(setting.getClassification().equalsIgnoreCase(LoanConstants.STANDARD)){
                for(NPADataDto dataDto:npaDataDtoList){
                    dataDto.setStandardProvision((dataDto.getStandardAmt() * setting.getProvision())/100);
                }
            }else if(setting.getClassification().equalsIgnoreCase(LoanConstants.SUB_STANDARD)){
                for(NPADataDto dataDto:npaDataDtoList){
                    dataDto.setSubstandardProvision((dataDto.getSubstandardAmt() * setting.getProvision())/100);
                }
            }
            else if(setting.getClassification().equalsIgnoreCase(LoanConstants.DOUBTFUL_1)){
                for(NPADataDto dataDto:npaDataDtoList){
                    dataDto.setDoubtful1Provision((dataDto.getDoubtful1Amt() * setting.getProvision())/100);
                }
            }
            else if(setting.getClassification().equalsIgnoreCase(LoanConstants.DOUBTFUL_2)){
                for(NPADataDto dataDto:npaDataDtoList){
                    dataDto.setDoubtful2Provision((dataDto.getDoubtful2Amt() * setting.getProvision())/100);
                }
            }
            else if(setting.getClassification().equalsIgnoreCase(LoanConstants.DOUBTFUL_3)){
                for(NPADataDto dataDto:npaDataDtoList){
                    dataDto.setDoubtful3Provision((dataDto.getDoubtful3Amt() * setting.getProvision())/100);
                }
            }
        });
    }

}
