package com.techvg.vks.trading.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SundryCreditorDto extends BaseEntityDto implements Serializable {
	private static final long serialVersionUID = 5238993004024820529L;

	private Date date;
	private Long dayBookId;
	private Long voucherId;
	private String particulars;
	private Double debit;
	private Double credit;
	private Double balance;
	private Date transDate;
	private String initials;
	private Long vendorId;
	private String ownerName;
    private Set<SundryCreditorTransactionDto> sundryCreditorTransaction;

	

	 public SundryCreditorDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted,Date date,Long dayBookId,Long voucherId,String particulars,
			Double debit,Double credit,Double balance,Date transDate,String initials,Long vendorId,String ownerName,Set<SundryCreditorTransactionDto> sundryCreditorTransaction)
	 
	 {
			super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
			
			this.date=date;
			this.dayBookId=dayBookId;
			this.voucherId=voucherId;
			this.particulars=particulars;
			this.debit=debit;
			this.credit=credit;
			this.balance=balance;
			this.transDate=transDate;
			this.initials=initials;
			this.vendorId=vendorId;
			this.ownerName=ownerName;
			this.sundryCreditorTransaction=sundryCreditorTransaction;
	 }
}
