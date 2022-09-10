package com.techvg.vks.loan.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LoanDemandDto extends BaseEntityDto implements Serializable {
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
	 private String landGatNo;
	 private String status;
	private String tharavNo;
	 private Double costOfInvestment;
	
	public LoanDemandDto(@Null Long id,@Null LocalDateTime created,@Null String createdBy,@Null LocalDateTime lastModified,
			@Null String lastModifiedBy, Boolean isDeleted, String season,  Integer year, Double loanAmount, String cropName, String memberFullName,
			Integer cropLandAreaHector, Integer cropLandAreaR, Long memberId, Long loanProductId, Long cropRegistrationId,
			Double adjustedDemand, Double maxAllowed, Long cropLoanDemandId, Long landId,String status,Double costOfInvestment,String landGatNo ) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
	
		this.season = season;
		this.year = year;
		this.loanAmount = loanAmount;
		this.cropName=cropName;
		this.memberFullName=memberFullName;
		this.cropLandAreaHector = cropLandAreaHector;
		this.cropLandAreaR = cropLandAreaR;
		this.memberId = memberId;
		this.loanProductId = loanProductId;
		this.cropRegistrationId = cropRegistrationId;
		this.adjustedDemand = adjustedDemand;
		this.maxAllowed = maxAllowed;
		this.cropLoanDemandId = cropLoanDemandId;
		this.landId = landId;
		this.status=status;
		this.costOfInvestment=costOfInvestment;
		this.landGatNo=landGatNo;
	}

	



	
	
	 
	
	 
	 
	 

}
