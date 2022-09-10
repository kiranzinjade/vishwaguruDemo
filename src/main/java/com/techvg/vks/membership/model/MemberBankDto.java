package com.techvg.vks.membership.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

import com.techvg.vks.base.BaseEntityDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class MemberBankDto  extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 8506582894893716606L;

	@NotBlank
	private String districtBankName;
	@NotBlank
	private	 String branchName;
	private	 Long accountNumber;
	private String ifsccode;
	
	
	@Builder
	public MemberBankDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified, @Null String lastModifiedBy, Boolean isDeleted,
			 @NotBlank String districtBankName, @NotBlank String branchName, Long accountNumber, String ifsccode) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.districtBankName = districtBankName;
		this.branchName = branchName;
		this.accountNumber = accountNumber;
		this.ifsccode=ifsccode;
	}
	
	
	
	


	
	

}
