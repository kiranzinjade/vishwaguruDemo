package com.techvg.vks.loan.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor

public class AmortizationDto  extends BaseEntityDto implements Serializable {
	private static final long serialVersionUID = -6332363734962311441L;

	private Date installmentDate;
	private Double outstandingPrinciple;
	private Double interestAmt;
	private Double principleEMI;
	private Double totalAmt;
	private String paymentStatus; // Paid, Overdue, OverDuePaid, Due
	private Double balPrincipleAmt;
	private Double balInterestAmt;

	@Builder
	public AmortizationDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy,@Null LocalDateTime lastModified, @Null String lastModifiedBy, Boolean isDeleted,Date installmentDate, Double outstandingPrinciple, Double interestAmt,
			Double principleEMI, Double totalAmt, String paymentStatus, double balPrincipleAmt, double balInterestAmt) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.installmentDate = installmentDate;
		this.outstandingPrinciple = outstandingPrinciple;
		this.interestAmt = interestAmt;
		this.principleEMI = principleEMI;
		this.totalAmt = totalAmt;
		this.paymentStatus = paymentStatus;
		this.balPrincipleAmt = balPrincipleAmt;
		this.balInterestAmt = balInterestAmt;
	}

}
