package com.techvg.vks.loan.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.Null;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class ShortTermSubsidyDto extends BaseEntityDto implements Serializable{
	
	private Double centralGovInterest;
	
	private Date centralGovDate;
	
	private Double stateGovInterest;
	
	private Date stateGovDate;
	
	private Double distBankInterest; 
	
	private Date distBankDate;
	
	private Long loanDetailsId;
	
	private String fullName;
	
	private Date loanClosureDate;
	
	private Long loanAccountNo;

	private Long grantAmt;
	
	private Long pendingAmt;
	
	public ShortTermSubsidyDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy,@Null LocalDateTime lastModified, @Null String lastModifiedBy, Boolean isDeleted,Double centralGovInterest, Date centralGovDate, Double stateGovInterest,
			Date stateGovDate, Double distBankInterest, Date distBankDate,Long loanDetailsId, String fullName, Date loanClosureDate, Long loanAccountNo, Long grantAmt, Long pendingAmt) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.centralGovInterest = centralGovInterest;
		this.centralGovDate = centralGovDate;
		this.stateGovInterest = stateGovInterest;
		this.stateGovDate = stateGovDate;
		this.distBankInterest = distBankInterest;
		this.distBankDate = distBankDate;
		this.loanDetailsId=loanDetailsId;
		this.fullName=fullName;
		this.loanClosureDate=loanClosureDate;
		this.loanAccountNo=loanAccountNo;
		this.grantAmt=grantAmt;
		this.pendingAmt=pendingAmt;
	}
	

}
