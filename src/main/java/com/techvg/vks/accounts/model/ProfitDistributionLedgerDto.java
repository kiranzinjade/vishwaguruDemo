package com.techvg.vks.accounts.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfitDistributionLedgerDto extends BaseEntityDto implements Serializable {
	private static final long serialVersionUID = -1230467019925531211L;
	
	private String accHeadCode;
	private Double amount;
	private Integer year;
	private Long ledgerAccountId;
	
	@Builder
	public ProfitDistributionLedgerDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted,
			String accHeadCode,Double amount,Integer year,Long ledgerAccountId) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.accHeadCode = accHeadCode;
		this.amount = amount;
		this.year = year;
		this.ledgerAccountId = ledgerAccountId;
	}


}
