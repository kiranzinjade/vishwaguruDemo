package com.techvg.vks.society.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.techvg.vks.base.BaseEntityDto;
import com.techvg.vks.trading.model.PurchaseRegisterDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BorrowingLedgerDto extends BaseEntityDto implements Serializable {
	private static final long serialVersionUID = 1853131313904688218L;
	
	
	private String purpose;
	private Long loanNo;
	private Date date;
	private Double loanAmt;
	private Integer duration;
	private Date dueDate;
	private Double interest;
	private String crop;
	private Long bankId;
	private String bankName;
	
	@Builder
	public BorrowingLedgerDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted,String bankName,
			String purpose, Long loanNo, Date date, Double loanAmt,Integer duration,Date dueDate, Double interest, String crop, Long bankId) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.purpose = purpose;
		this.loanNo = loanNo;
		this.date = date;
		this.date = date;
		this.loanAmt=loanAmt;
		this.duration=duration;
		this.dueDate=dueDate;
		this.interest=interest;
		this.crop=crop;
		this.bankId=bankId;
		this.bankName=bankName;

	}

}
