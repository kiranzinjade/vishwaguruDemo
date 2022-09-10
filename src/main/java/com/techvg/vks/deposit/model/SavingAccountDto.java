package com.techvg.vks.deposit.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class SavingAccountDto extends BaseEntityDto implements Serializable {
	private static final long serialVersionUID = 8506582894893716606L;
	
	private Date accountOpeningDate;
	
	private Date accountClosingDate;
	
	private Double balance;
	
	private Long accountNo;
	
	private String status;
	
	private Long depositAccountId;
	
	private String fullName;
	
	private Long memberId;
	
	public SavingAccountDto( @Null Long id, LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified,
			@Null String lastModifiedBy, Boolean isDeleted, Date accountOpeningDate, Date accountClosingDate, Double balance,
			Long accountNo, String status, Long depositAccountId,String fullName,Long memberId) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.accountOpeningDate = accountOpeningDate;
		this.accountClosingDate = accountClosingDate;
		this.balance = balance;
		this.accountNo = accountNo;
		this.status = status;
		this.depositAccountId = depositAccountId;
		this.fullName=fullName;
		this.memberId=memberId;
	}
	
	
	
	

}
