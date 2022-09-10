package com.techvg.vks.loan.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoanInterestDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 8506582894893716606L;

    private Long accountNo;
    private Long memberId;
    private String memberName;
    private Date loanDate;
    private Date lastPaymentDate;
    private Double totalCurrentOutstanding;
    private String npaClassification;
    private String loanType;
    private Date loanPlannedClosureDate;
    private Double outstandingPrincipal;
    private Double currentLoanInterest;
    
	public LoanInterestDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
						   String lastModifiedBy, Boolean isDeleted, Long accountNo, Long memberId, String memberName, Date loanDate,
						   Date lastPaymentDate, String npaClassification, Double totalCurrentOutstanding, String loanType,
						   Date loanPlannedClosureDate, Double currentLoanInterest) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.accountNo = accountNo;
		this.memberId = memberId;
		this.memberName = memberName;
		this.loanDate = loanDate;
		this.lastPaymentDate = lastPaymentDate;
		this.npaClassification = npaClassification;
		this.totalCurrentOutstanding=totalCurrentOutstanding;
		this.loanType=loanType;
		this.loanPlannedClosureDate=loanPlannedClosureDate;
		this.currentLoanInterest=currentLoanInterest;
	}
    
    
}
