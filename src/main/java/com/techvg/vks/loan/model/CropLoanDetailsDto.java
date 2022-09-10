package com.techvg.vks.loan.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CropLoanDetailsDto extends BaseEntityDto implements Serializable {
	 private static final long serialVersionUID = 8506582894893716606L;
		private String name;
		String loanAccountNo;
		double loanAmt;
		String loanType;
		String cropName;
		Integer installmentFrequency;
	    Date loanEffectiveDate;
		Date loanCloserDate;
	// disbursement date and installment date are not mapped 
	//  value is null------> created after mapping in dibursementMapper
		Date disbursementDate;
		Date installmentDate;
		Date currentDate = new Date();
		Integer landAreaR;
		Integer landAreaHector;
		private double duration;
		private double interestRate;
		Double totalInterest;
		 private String landGatno;
		 private Long memberId;
		 private Long loanProductId;
		 private String productName;
		 private Double maxAllowed;
		 private String season;
		 
		public CropLoanDetailsDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
				String lastModifiedBy, Boolean isDeleted, String name, String loanAccountNo, double loanAmt,
				String loanType, String cropName, Integer installmentFrequency, Date loanEffectiveDate, Date loanCloserDate,
				Date disbursementDate, Date installmentDate, Date currentDate, double duration, double interestRate,
				Double totalInterest,Integer landAreaR,String landGatno,Long memberId,Long loanProductId,Integer landAreaHector,Double maxAllowed,String season) {
			super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
			this.name = name;
			this.loanAccountNo = loanAccountNo;
			this.loanAmt = loanAmt;
			this.loanType = loanType;
			this.cropName = cropName;
			this.installmentFrequency = installmentFrequency;
			this.loanEffectiveDate = loanEffectiveDate;
			this.loanCloserDate = loanCloserDate;
			this.disbursementDate = disbursementDate;
			this.installmentDate = installmentDate;
			this.currentDate = currentDate;
			this.duration = duration;
			this.interestRate = interestRate;
			this.totalInterest = totalInterest;
			this.landAreaR=landAreaR;
			this.landGatno=landGatno;
			this.memberId=memberId;
			this.loanProductId=loanProductId;
			this.landAreaHector=landAreaHector;
			this.maxAllowed=maxAllowed;
			this.season=season;
		}
}
