package com.techvg.vks.society.model;

import java.time.LocalDateTime;
import java.util.Date;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SocietyBankTransactionDto extends BaseEntityDto{

	private Date date;
	private String particulars;
	private Date transactionDate;
	private Double debit;
	private Double credit;
	private Double balance;
	private String narration;
	private String initial;
	private Long bankId;
	private Long voucherNo;
	private String transType;
	
	
	@Builder
	public SocietyBankTransactionDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, Date date, String particulars, Date transactionDate, Double debit,
			Double credit, Double balance, String narration, String initial,Long bankId, Long voucherNo, String transType ) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.date = date;
		this.particulars = particulars;
		this.transactionDate = transactionDate;
		this.debit = debit;
		this.credit = credit;
		this.balance = balance;
		this.narration = narration;
		this.initial = initial;
		this.bankId = bankId;
		this.voucherNo = voucherNo;
		this.transType = transType;
	}
	
	
}
