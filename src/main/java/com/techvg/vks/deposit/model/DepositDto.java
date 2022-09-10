package com.techvg.vks.deposit.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class DepositDto extends BaseEntityDto implements Serializable   {
	private static final long serialVersionUID = 8506582894893716606L;
	
	private Long receiptNo;
	private Long accountNo;
	private Long accrualAccountNo;
	private Double recurringAmount;
	private Integer depositFrequency;
	private Date maturityDate;
	private Double interestEarned;
	private Double maturityAmount;
	private Date depositDate;
	private Double depositAmount;
	private String depositStatus;
	private Date depositClosingDate;

	
	
	private Boolean reinvestmentStatus;
	private String fullName;
	private String name;
	private Long memberId;
	private Long depositAccountId;
	private Integer lockInPeriod;
	private String accountType;


	public DepositDto(@Null Long id, @Null LocalDateTime created,@Null String createdBy, @Null LocalDateTime lastModified,
			@Null String lastModifiedBy, Boolean isDeleted, Long receiptNo, Long accountNo, Double recurringAmount, Long accrualAccountNo,
            Integer depositFrequency, Date maturityDate, Double interestEarned, Double maturityAmount, Date depositDate, Double depositAmount,
			String depositStatus, Date depositClosingDate, Boolean reinvestmentStatus,String fullName,String name,Long memberId,Long depositAccountId,Integer lockInPeriod,String accountType)  

	 {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.receiptNo = receiptNo;
		this.accountNo = accountNo;
		this.accrualAccountNo = accrualAccountNo;
		this.recurringAmount = recurringAmount;
		this.depositFrequency = depositFrequency;
		this.maturityDate = maturityDate;
		this.interestEarned = interestEarned;
		this.maturityAmount = maturityAmount;
		this.depositDate = depositDate;
		this.depositAmount = depositAmount;
		this.depositStatus=depositStatus;
		this.depositClosingDate=depositClosingDate;
		this.reinvestmentStatus=reinvestmentStatus;


		this.fullName=fullName;
		this.name=name;
		this.memberId=memberId;
		this.depositAccountId=depositAccountId;
		this.lockInPeriod=lockInPeriod;
		this.accountType=accountType;
	}

}
