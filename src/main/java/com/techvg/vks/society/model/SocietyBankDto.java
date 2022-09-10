package com.techvg.vks.society.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class SocietyBankDto extends BaseEntityDto implements Serializable {
	private static final long serialVersionUID = 8506582894893716606L;

	@NotBlank
	private String bankName;

	/*
	 * @Null private Integer bankId;
	 */

	private String branchName;

	@NotBlank
	private String ifsccode;
	private String status;
	private Long depositTypeId;
	private Long accountNumber;
	private String accountType;
	private  String accHeadCode;
	private String accountName;

	@Builder
	public SocietyBankDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, @NotBlank String bankName, String branchName,String accountType,
			@NotBlank String ifsccode, String status,Long depositTypeId, Long accountNumber,  String accHeadCode, String accountName) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.bankName = bankName;
		this.branchName = branchName;
		this.ifsccode = ifsccode;
		this.status = status;
		this.depositTypeId=depositTypeId;
		this.accountType=accountType;
		this.accountNumber = accountNumber;
		 this.accHeadCode=accHeadCode;
		this.accountName=accountName;
	}

}
