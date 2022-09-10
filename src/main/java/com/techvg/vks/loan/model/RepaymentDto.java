package com.techvg.vks.loan.model;

import com.techvg.vks.accounts.model.VouchersDto;
import com.techvg.vks.base.BaseEntityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor

public class RepaymentDto  extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 8506582894893716606L;
    
    private Date installmentDate;

    private Date nextInstallmentDate;
	
    private Integer installmentNo;
	
    private Double installmentAmt;
	
    private Double openingPrinciple;
	
    private Double closingPrinciple;
    
    private Double principlePaid;

    private Double totalInterest;
	
    private Double interestPaid;
	
    private Double balanceInterest;

    private Double overDueAmt;
	
	private String installmentStatus;
	
	private Long voucherNo;
	private Double penalty;
	
	private	Double foreClosureCharge;
	
	private Double surCharge;
		
	private Long loanId;

	private VouchersDto vouchersDto;
    
	@Builder
	public RepaymentDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, Date installmentDate, Integer installmentNo,
			Double installmentAmt, Double openingPrinciple, Double closingPrinciple, Double principlePaid,
			Double totalInterest, Double interestPaid, Double balanceInterest, String installmentStatus, Double penalty,
			Double foreClosureCharge, Double surCharge, Long loanId, Long voucherNo, VouchersDto vouchersDto) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.installmentDate = installmentDate;
		this.installmentNo = installmentNo;
		this.installmentAmt = installmentAmt;
		this.openingPrinciple = openingPrinciple;
		this.closingPrinciple = closingPrinciple;
		this.principlePaid = principlePaid;
		this.totalInterest = totalInterest;
		this.interestPaid = interestPaid;
		this.balanceInterest = balanceInterest;
		this.installmentStatus = installmentStatus;
		this.penalty = penalty;
		this.foreClosureCharge = foreClosureCharge;
		this.surCharge = surCharge;
		this.loanId = loanId;
		this.voucherNo = voucherNo;
		this.vouchersDto = vouchersDto;
	}

    
	

}
