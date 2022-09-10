package com.techvg.vks.trading.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import com.techvg.vks.accounts.model.VoucherDetailsDto;
import com.techvg.vks.base.BaseEntityDto;
import com.techvg.vks.trading.domain.SundryDebtor;
import com.techvg.vks.trading.domain.SundryDebtorTransaction;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SundryDebtorTransactionDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 3045671664203584634L;
 
	    private Date date;
	    private Date transactionDate;
	    private Double debit;
	    private Double credit;
	    private Double balance;
	    private String particulars;
	    private Long debtorId;
	    
	    
		public SundryDebtorTransactionDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
				String lastModifiedBy, Boolean isDeleted, Date date, Date transactionDate, Double debit, Double credit,
				Double balance, String particulars,Long debtorId) {
			super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
			this.date = date;
			this.transactionDate = transactionDate;
			this.debit = debit;
			this.credit = credit;
			this.balance = balance;
			this.particulars = particulars;
			this.debtorId=debtorId;
		}
	    
	    
}
