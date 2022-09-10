 package com.techvg.vks.loan.model;

 import com.techvg.vks.base.BaseEntityDto;
 import lombok.Builder;
 import lombok.Data;
 import lombok.NoArgsConstructor;

 import java.io.Serializable;
 import java.time.LocalDateTime;
 import java.util.Date;
 import java.util.Set;

@Data
@NoArgsConstructor

public class LoanDetailsDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 8506582894893716606L;

	 private Long loanAccountNo;
	 private double loanAmt;
	 private String loanType;
	 private String loanStatus;
	 private Date loanInstallStartDate;
	 private Date loanInstallEndDate;
	 private String installmentFrequency;
	 private Date loanCloserDate;
	 private Date loanEffectiveDate;
	 private String loanClassification;
	 private Date loanPlannedClosureDate;
	 private String societyMeetingNo;
	 private Long resolutionNo;
	 private Date resolutionDate;
	 private Double costOfInvestment;
	 private Double benifitingArea;
	 private Long dccbLoanNo;
	 private Long mortgageDeedNo;
	 private Date mortgageDate;
	 private Double extentMorgage;
	 private Set<DisbursementDto> disbursementDtoSet;
	 private Set<RepaymentDto> repaymentDtoSet;

	 private Long loanProductId;
	 private Long memberId;
	private String loanAccName;
	private String parentAccHeadCode;
	private Long parentAccId;
	private Long loanDemandId;
	private String disbursementStatus;//full,partial
	private Double disbursementAmt;
	private String landGatno;
	private String firstName;
	private String middleName;
	private String lastName;
	private String fullName;
	private Long cropId;
	
	
	 @Builder
	public LoanDetailsDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, Long loanAccountNo, double loanAmt, String loanType,
			String loanStatus, Date loanInstallStartDate, Date loanInstallEndDate, String installmentFrequency,
			Date loanCloserDate, Date loanEffectiveDate, String loanClassification, Date loanPlannedClosureDate,
			String societyMeetingNo, Long resolutionNo, Date resolutionDate, Double costOfInvestment,
			Double benifitingArea, Long dccbLoanNo, Long mortgageDeedNo, Date mortgageDate,
			Double extentMorgage, Set<DisbursementDto> disbursementDtoSet, Set<RepaymentDto> repaymentDtoSet,
	 		Long loanProductId, Long memberId, String loanAccName, String parentAccHeadCode, Long parentAccId,
			Double disbursementAmt, String disbursementStatus,String landGatno,String firstName,String middleName,
			String lastName,String fullName,Long cropId,Long loanDemandId) {

		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.loanAccountNo = loanAccountNo;
		this.loanAmt = loanAmt;
		this.loanType = loanType;
		this.loanStatus = loanStatus;
		this.loanInstallStartDate = loanInstallStartDate;
		this.loanInstallEndDate = loanInstallEndDate;
		this.installmentFrequency = installmentFrequency;
		this.loanCloserDate = loanCloserDate;
		this.loanEffectiveDate = loanEffectiveDate;
		this.loanClassification = loanClassification;
		this.loanPlannedClosureDate = loanPlannedClosureDate;
		this.societyMeetingNo = societyMeetingNo;
		this.resolutionNo = resolutionNo;
		this.resolutionDate = resolutionDate;
		this.costOfInvestment = costOfInvestment;
		this.benifitingArea = benifitingArea;
		this.dccbLoanNo = dccbLoanNo;
		this.mortgageDeedNo = mortgageDeedNo;
		this.mortgageDate = mortgageDate;
		this.extentMorgage = extentMorgage;
		this.disbursementDtoSet = disbursementDtoSet;
		this.repaymentDtoSet = repaymentDtoSet;
		this.loanProductId = loanProductId;
		this.memberId = memberId;
		this.loanAccName = loanAccName;
		this.parentAccHeadCode = parentAccHeadCode;
		this.parentAccId = parentAccId;
		this.disbursementAmt = disbursementAmt;
		this.disbursementStatus = disbursementStatus;
		this.landGatno=landGatno;
		this.firstName=firstName;
		this.middleName=middleName;
		this.lastName=lastName;
		this.fullName=fullName;
		this.cropId=cropId;
		this.loanDemandId=loanDemandId;
	}
	 
 
	
	


}
