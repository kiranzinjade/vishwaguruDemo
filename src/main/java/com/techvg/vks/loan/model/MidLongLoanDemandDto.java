package com.techvg.vks.loan.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MidLongLoanDemandDto extends BaseEntityDto implements Serializable{
	 private static final long serialVersionUID = 8506582894893716606L;
	 
	 private String memberFullName;
	 private String productName;
	 private Double loanAmount;
	 private Double annualIncome;
	 private Long memberId;
	 private Long productId;
	 private String status;
	 private Double costOfInvestment;
	 
	public MidLongLoanDemandDto(@Null Long id,@Null LocalDateTime created,@Null String createdBy,@Null LocalDateTime lastModified,
			@Null String lastModifiedBy, Boolean isDeleted,Double loanAmount, Double annualIncome,
			String memberFullName, String productName, Long memberId, Long productId, String status, Double costOfInvestment) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		
		this.loanAmount = loanAmount;
		this.annualIncome = annualIncome;
		this.memberFullName=memberFullName;
		this.productName=productName;
		this.memberId = memberId;
		this.productId = productId;
		this.status=status;
		this.costOfInvestment=costOfInvestment;
	}
	 
	 

}
