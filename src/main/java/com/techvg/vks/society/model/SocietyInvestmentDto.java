package com.techvg.vks.society.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class SocietyInvestmentDto extends BaseEntityDto implements Serializable{
	private static final long serialVersionUID = 8506582894893716606L;
	private Integer boardResolutionNo;
	private Date maturityDate;
	private Date depositDate;
	private Date boardResolutionDate;
	private Double depositAmount;
	private Double maturityAmount;
	private Double interestAmount;
	private Long societyInvestmentId;
	private Long accountNumber;
	private String nomenclature;
	private String bankName;
	private String branchName;
	
	@Builder
	public SocietyInvestmentDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
								String lastModifiedBy, Boolean isDeleted, Integer boardResolutionNo, Date maturityDate, Date depositDate,
								Date boardResolutionDate, Double depositAmount, Double maturityAmount, Double interestAmount, Long societyInvestmentId, Long accountNumber, String nomenclature,String bankName,String branchName) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.boardResolutionNo = boardResolutionNo;
		this.maturityDate = maturityDate;
		this.depositDate = depositDate;
		this.boardResolutionDate = boardResolutionDate;
		this.depositAmount=depositAmount;
		this.maturityAmount=maturityAmount;
		this.interestAmount=interestAmount;
		this.societyInvestmentId=societyInvestmentId;
		this.accountNumber=accountNumber;
		this.nomenclature=nomenclature;
		this.bankName=bankName;
		this.branchName=branchName;
		
	}
	
	
	
}
