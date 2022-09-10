package com.techvg.vks.trading.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class SundryCreditorTransactionDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 3045671664203584634L;
    
    private Date date;
    private Date transactionDate;
    private Double debit;
    private Double credit;
    private Double balance;
    private String particulars;
    
    
	public SundryCreditorTransactionDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, Date date, Date transactionDate, Double debit, Double credit,
			Double balance, String particulars) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.date = date;
		this.transactionDate = transactionDate;
		this.debit = debit;
		this.credit = credit;
		this.balance = balance;
		this.particulars = particulars;
	}
    
	
    
}
