package com.techvg.vks.society.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.techvg.vks.accounts.model.VouchersDto;
import com.techvg.vks.base.BaseEntityDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BankBookPrintDto  extends BaseEntityDto implements Serializable{
	private static final long serialVersionUID = 8506582894893716606L;

	private List<SocietyBankTransactionDto> list;
	private double openingBal;
	private double totalCre;
	private double totalDeb;
	private String bankName;
	private String accountType;
	private Long accountNum;
	
	
	
	@Builder
	public BankBookPrintDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, List<SocietyBankTransactionDto> list, double openingBal,
			double totalCre, double totalDeb, String bankName, String accountType, Long accountNum) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.list = list;
		this.openingBal = openingBal;
		this.totalCre = totalCre;
		this.totalDeb = totalDeb;
		this.bankName = bankName;
		this.accountType = accountType;
		this.accountNum = accountNum;
	}
	
	
	
}
