package com.techvg.vks.loan.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Null;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoanWatapDto extends BaseEntityDto implements Serializable {
	   private static final long serialVersionUID = 8506582894893716606L;
		 
	 private String cropName;
	 private String memberFullName;
	 private String season;
	 private  Integer year;
	 private Double loanAmount;
	 private Integer cropLandAreaHector;
	 private Integer cropLandAreaR;
	 private Long memberId;
	 private Long loanProductId;
	 private Long cropRegistrationId;
	 private Double adjustedDemand;
	 private Double maxAllowed;
	 private Long cropLoanDemandId;
	 private Long landId;
	 private String status;
	 private String tharavNo;
	 private Double costOfInvestment;
	 private Integer slot;
	 
	public LoanWatapDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, String cropName, String memberFullName, String season,
			Integer year, Double loanAmount, Integer cropLandAreaHector, Integer cropLandAreaR, Long memberId,
			Long loanProductId, Long cropRegistrationId, Double adjustedDemand, Double maxAllowed,
			Long cropLoanDemandId, Long landId, String status, String tharavNo, Double costOfInvestment,Integer slot) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.cropName = cropName;
		this.memberFullName = memberFullName;
		this.season = season;
		this.year = year;
		this.loanAmount = loanAmount;
		this.cropLandAreaHector = cropLandAreaHector;
		this.cropLandAreaR = cropLandAreaR;
		this.memberId = memberId;
		this.loanProductId = loanProductId;
		this.cropRegistrationId = cropRegistrationId;
		this.adjustedDemand = adjustedDemand;
		this.maxAllowed = maxAllowed;
		this.cropLoanDemandId = cropLoanDemandId;
		this.landId = landId;
		this.status = status;
		this.tharavNo = tharavNo;
		this.costOfInvestment = costOfInvestment;
		this.slot = slot;
	}
	 
	 
	
	
	
}