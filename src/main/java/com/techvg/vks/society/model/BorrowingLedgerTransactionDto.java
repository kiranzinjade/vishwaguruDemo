package com.techvg.vks.society.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BorrowingLedgerTransactionDto extends BaseEntityDto implements Serializable {
	private static final long serialVersionUID = -6574419370598363520L;

	private Date transactionDate;

	private String particulars;

	private Double debit;

	private Double credit;

	private Double balance;
	
	private String initials;
	
	private String remarks;

	private Integer noOfDays;

	private Double totalInterest;

	private Double interestPaid;

	private Double interestDue;

	private Long borrowingId;

	@Builder
	public BorrowingLedgerTransactionDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, Date transactionDate, String particulars, Double debit, Double credit,
			Double balance, String initials,String remarks, Integer noOfDays, Double totalInterest, Double interestPaid, Double interestDue,
			Long borrowingId) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.transactionDate = transactionDate;
		this.particulars = particulars;
		this.debit = debit;
		this.credit = credit;
		this.balance = balance;
		this.initials=initials;
		this.remarks=remarks;
		this.noOfDays = noOfDays;
		this.totalInterest = totalInterest;
		this.interestPaid = interestPaid;
		this.interestDue = interestDue;
		this.borrowingId = borrowingId;

	}

}
