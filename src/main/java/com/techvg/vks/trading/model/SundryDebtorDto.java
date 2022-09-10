package com.techvg.vks.trading.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import com.techvg.vks.accounts.model.VoucherDetailsDto;
import com.techvg.vks.base.BaseEntityDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SundryDebtorDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 3045671664203584634L;

    private Date date;
    private Date transactionDate;
    private Double debit;
    private Double credit;
    private Double balance;
    private String particulars;
    private Long daybookId;
    private Long voucherId;
    private Long memberId;
    private String fullName;
    private Set<SundryDebtorTransactionDto> sundryDebtorTransaction;
    
  
	public SundryDebtorDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, Date date, Date transactionDate, Double debit, Double credit,
			String fullName,Double balance, String particulars, Long daybookId, Long voucherId, Long memberId,Set<SundryDebtorTransactionDto> sundryDebtorTransaction) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.date = date;
		this.transactionDate = transactionDate;
		this.debit = debit;
		this.credit = credit;
		this.balance = balance;
		this.particulars = particulars;
		this.daybookId = daybookId;
		this.voucherId = voucherId;
		this.memberId = memberId;
		this.fullName=fullName;
		this.sundryDebtorTransaction=sundryDebtorTransaction;
	}
    
    
    
}
