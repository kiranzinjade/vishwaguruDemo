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

public class DisbursementDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 8506582894893716606L;

	 private Date disbursementDate;
	 private Integer disbursementNo;
	 private double disbursementAmt;
	 private String paymentMode;
	 private String disbursementStatus;
	 private String loanType;
	 private Long loanDetailsId;
	 private Long voucherNo;
	 
	 @Builder
	 public DisbursementDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified, @Null String lastModifiedBy, Boolean isDeleted,Date disbursementDate, Integer disbursementNo, double disbursementAmt, String paymentMode,
			String disbursementStatus,String loanType, Long loanDetailsId,Long voucherNo) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.disbursementDate = disbursementDate;
		this.disbursementNo = disbursementNo;
		this.disbursementAmt = disbursementAmt;
		this.paymentMode = paymentMode;
		this.disbursementStatus = disbursementStatus;
		this.loanType=loanType;
		this.loanDetailsId = loanDetailsId;
		this.voucherNo=voucherNo;
	}

	


}
