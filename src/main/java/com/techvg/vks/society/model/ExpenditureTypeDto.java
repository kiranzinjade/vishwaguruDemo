package com.techvg.vks.society.model;

import java.time.LocalDateTime;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExpenditureTypeDto extends BaseEntityDto {

	String expenditureType; // eg office -> Salary mgmt, Rent, Insurance, Repair, Stationary etc
	String expenseCategory; // Office, Agri products related, PDS, Non-PDS, Day-Meal
	String expenditureDesc;
	
	@Builder
	public ExpenditureTypeDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, String expenditureType, String expenditureDesc, String expenseCategory) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.expenditureType = expenditureType;
		this.expenditureDesc = expenditureDesc;
		this.expenseCategory = expenseCategory;
	}
	
	
	
	
	
	
	
}
