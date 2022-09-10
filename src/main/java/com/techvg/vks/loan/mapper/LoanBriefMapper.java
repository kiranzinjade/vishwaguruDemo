package com.techvg.vks.loan.mapper;

import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.model.LoanBriefDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanBriefMapper {

    LoanBriefDto toDto(LoanDetails domain);
    List<LoanBriefDto> toDtoList(List<LoanDetails> domainList);

    @AfterMapping
    default void updateLoanBriefDto(@MappingTarget LoanBriefDto loanBriefDto, LoanDetails domain ) {
        loanBriefDto.setLoanProductName(domain.getLoanProduct().getProductName());
        loanBriefDto.setId(domain.getId());
        loanBriefDto.setBalanceAmt(domain.getLoanAmt());
        loanBriefDto.setPaidAmt(0.0);
        loanBriefDto.setMemberId(domain.getMember().getId());
        loanBriefDto.setFullName(domain.getMember().getFirstName() + " " + domain.getMember().getLastName());
        domain.getRepayment().forEach(action->{
            loanBriefDto.setBalanceAmt(action.getClosingPrinciple() + action.getBalanceInterest());
            loanBriefDto.setPaidAmt(action.getPrinciplePaid() + action.getInterestPaid());
        });
        loanBriefDto.setInterest(domain.getLoanProduct().getInterestRate());
        loanBriefDto.setDuration(domain.getLoanProduct().getDuration());
    }
}
