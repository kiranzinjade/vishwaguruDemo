package com.techvg.vks.deposit.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.constraints.Null;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PrintPassbookDto extends BaseEntityDto implements Serializable {
	private static final long serialVersionUID = 8506582894893716606L;

	private Long accountNo;
	private Date printingDate;
	private String printingStatus;
	private Integer depositLedgerMaxId;

	@Builder
	public PrintPassbookDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified,
			@Null String lastModifiedBy, Boolean isDeleted, Long accountNo, Date printingDate, String printingStatus,
							Integer depositLedgerMaxId) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.accountNo = accountNo;
		this.printingDate = printingDate;
		this.printingStatus = printingStatus;
		this.depositLedgerMaxId = depositLedgerMaxId;
	}

}
