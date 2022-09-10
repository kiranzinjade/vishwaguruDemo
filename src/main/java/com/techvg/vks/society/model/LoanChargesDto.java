package com.techvg.vks.society.model;


import com.techvg.vks.base.BaseEntityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LoanChargesDto extends BaseEntityDto{
	
    private String chargesName;
	private Double chargesValue;
	private String chargesType;
	private String chargesCategory;
	private String description;
	private String loanType;
	
	@Builder

	public LoanChargesDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy,
						  @Null LocalDateTime lastModified, @Null String lastModifiedBy, Boolean isDeleted,
						  String chargesName, Double chargesValue, String chargesType, String chargesCategory,
						  String description, String loanType) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.chargesName = chargesName;
		this.chargesValue = chargesValue;
		this.chargesType = chargesType;
		this.chargesCategory = chargesCategory;
		this.description = description;
		this.loanType = loanType;
	}
}
