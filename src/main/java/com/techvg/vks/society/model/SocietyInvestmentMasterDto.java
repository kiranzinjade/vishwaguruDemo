package com.techvg.vks.society.model;

import java.time.LocalDateTime;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SocietyInvestmentMasterDto extends BaseEntityDto{
	
	private String nomenclature;
	private Integer period;
	private Double interest;
	private Long bankId;
	private String bankName;
	private String branchName;
	private Long accountNumber;
	private Long depositTypeId;
	private String accountType;
	
	@Builder
	public SocietyInvestmentMasterDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, String nomenclature,Long bankId, Long depositTypeId,
			Integer period, Double interest,String bankName,String branchName,Long accountNumber,String accountType) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.nomenclature = nomenclature;
		this.bankId=bankId;
		this.depositTypeId=depositTypeId;
		this.period = period;
		this.interest = interest;
		this.bankName=bankName;
		this.branchName=branchName;
		this.accountNumber=accountNumber;
		this.accountType=accountType;
	}
	
	
}
