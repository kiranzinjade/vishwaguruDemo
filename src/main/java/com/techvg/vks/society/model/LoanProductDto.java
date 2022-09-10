package com.techvg.vks.society.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class LoanProductDto extends BaseEntityDto{
	
	private String productType;
	
	private String productName;
	
	private String status;
	
	private Date validFrom;
	
	private Date validTo;
	
	private Integer duration;
	
	private Double surcharge;
	
	private Double interestRate;
	
	private Double penaltyInterest;
	
	private Integer numberOfInstallment;
	
	private String resolutionNo;

	private Date resolutionDate;
	
	private Integer noOfDisbursement;

	private Long cropLoanDemandId;

	private String cropLoanDemandName;
	
	private Date lastDateOfRepayment;
	
	private Double maxLoanAmount;
	
	private String unitSize;

	private String parentAccHeadCode;

	private Long parentAccHeadId;

	private String accHeadCode;

	private Long loanNumber;

	private Double borrowingInterestRate;
	
	private Integer slot;

	public LoanProductDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
						  String lastModifiedBy, Boolean isDeleted, String productType, String productName, String status, Date validFrom,
						  Date validTo, Integer duration, Double surcharge, Double interestRate, Double penaltyInterest,
						  Integer numberOfInstallment,String resolutionNo,Integer noOfDisbursement, Date resolutionDate,
						  Date lastDateOfRepayment,Double maxLoanAmount,String unitSize ,Integer slot) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.productType = productType;
		this.productName = productName;
		this.status = status;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.duration = duration;
		this.surcharge = surcharge;
		this.interestRate = interestRate;
		this.penaltyInterest = penaltyInterest;
		this.numberOfInstallment=numberOfInstallment;
		this.resolutionNo=resolutionNo;
		this.noOfDisbursement=noOfDisbursement;
		this.resolutionDate = resolutionDate;
		this.lastDateOfRepayment=lastDateOfRepayment;
		this.maxLoanAmount=maxLoanAmount;
		this.unitSize=unitSize;
		this.slot=slot;
	}

}
