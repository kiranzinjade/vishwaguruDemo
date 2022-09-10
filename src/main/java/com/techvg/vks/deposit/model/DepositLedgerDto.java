package com.techvg.vks.deposit.model;

import com.techvg.vks.base.BaseEntityDto;
import com.techvg.vks.deposit.domain.PrintPassbook;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public class DepositLedgerDto extends BaseEntityDto implements Serializable {
	private static final long serialVersionUID = -7552008737104958851L;

	private Date depositDate;

	private Long accountNo;

	private Double debitAmount;

	private Double creditAmount;

	private Double balanceAmount;

	private String narration;

	private Integer voucherId;

	private String particulars;

	private Set<PrintPassbook> printPassbook;
	
	private Date date;

	private Boolean dayBookCreated;
	
	private Long depositId;
	
	private Long savingAccountId;
	 
	private String fullName;
	
	@Builder
	public DepositLedgerDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, Date depositDate, Long accountNo, Double debitAmount,
			Double creditAmount, Double balanceAmount, String narration, Integer voucherId,
			String particulars,Set<PrintPassbook> printPassbook,Date date,Long depositId,Long savingAccountId,
							String fullName, Boolean dayBookCreated)
	{
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.depositDate = depositDate;
		this.accountNo = accountNo;
		this.debitAmount = debitAmount;
		this.creditAmount = creditAmount;
		this.balanceAmount = balanceAmount;
		this.narration = narration;
		this.voucherId = voucherId;
		this.particulars = particulars;
		this.printPassbook = printPassbook;
		this.date = date;
		this.depositId = depositId;
		this.savingAccountId = savingAccountId;
		this.fullName = fullName;
		this.dayBookCreated = dayBookCreated;
	}

}
